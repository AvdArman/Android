package com.instigatemobile.grapes.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.instigatemobile.grapes.models.FileModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReadWriteJson {

    private final String JSON_TAG = "Json error";
    private Context mContext;

    public ReadWriteJson(Context context) {
        mContext = context;
    }

    public void mergeJsonFiles() {
        List<String> fileNames = getFileNames();
        String json;
        String folder = "remote";
        if (fileNames != null) {
            for (String fileName : fileNames) {
                json = readJson(folder, fileName);
                fillListFromJson(json, fileName);
            }
        }
        writeListIntoFile(Util.mRemoteFileList, Util.mRemoteFilePath);
    }

    public void writeListIntoFile(List<FileModel> fileList, String filePath) {
        final String jsonRootElem = "files";
        JSONArray macArray = new JSONArray();
        JSONArray jsonArray = new JSONArray();
        JSONObject tmpObj = new JSONObject();
        for (FileModel file : fileList) {
            try {
                JSONObject fileObj = new JSONObject();
                fileObj.put(Util.NAME, file.getName());
                fileObj.put(Util.PATH, file.getPath());
                fileObj.put(Util.SIZE, file.getSize());
                fileObj.put(Util.EXTENSION, file.getExtension());
                fileObj.put(Util.ICON, file.getIcon());
                fileObj.put(Util.DATE, file.getDate());
                List<String> macList = file.getMacAddresses();
                if (!macList.isEmpty()) {
                    for (String mac : macList) {
                        macArray.put(mac);
                    }
                }
                fileObj.put(Util.MAC, macArray);
                jsonArray.put(fileObj);
            } catch (JSONException e) {
                Log.e(JSON_TAG, "Invalid json" + e.toString());
            }
        }
        try {
            tmpObj.put(jsonRootElem, jsonArray);
        } catch (JSONException e) {
            Log.e(JSON_TAG, "Invalid json" + e.toString());
        }
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(String.valueOf(tmpObj));
        writeJsonInFile(String.valueOf(jsonElement), filePath);
    }

    public String readJson(String folder, String fileName) {
        File file;
        if (folder != null) {
            File root = new File(folder);
            file = new File(root, fileName);
            Util.mRemoteFilePath = file.getAbsolutePath();
        }
        final StringBuilder json = new StringBuilder();
        try {
            FileInputStream fileInputStream =  mContext.openFileInput(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(fileInputStream)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                json.append(line);
            }
        } catch (IOException e) {
            String TAG_FILE_READ = "Failed read";
            Log.e(TAG_FILE_READ, "Cannot read from file : " + e.toString());
        }
        return String.valueOf(json);
    }

    private void fillListFromJson(@NonNull String json, String fileName) {
        JSONArray jsonArray;
        try {
            if (json.length() > 4) {
                JSONObject tmpObj = new JSONObject(json);
                jsonArray = tmpObj.getJSONArray("files");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject tmpObject = jsonArray.getJSONObject(i);
                    tmpObject.getJSONArray("macAddr");
                    FileModel file = new FileModel(tmpObject.getString(Util.SIZE),
                            tmpObject.getString(Util.ICON),
                            tmpObject.getString(Util.NAME),
                            tmpObject.getString(Util.PATH),
                            tmpObject.getString(Util.DATE),
                            tmpObject.getString(Util.EXTENSION));
                    file.setMacAddresses(getMacList(tmpObject));
                    if (isUnique(file, fileName)) {
                        Util.mRemoteFileList.add(file);
                    }
                }
            }
        } catch (JSONException e) {
            Log.e(JSON_TAG, "Can not read from json file" + e.toString());
        }
        Util.mCurrentList = Util.mRemoteFileList;
        Util.mBeforeSearchList = Util.mCurrentList;
    }

    private boolean isUnique(FileModel file, String fileName) {
        int size = Util.mRemoteFileList.size();
        List<FileModel> tmp = Util.mRemoteFileList;
        String mac = fileName.replace("files.json", "");
        for (int i = 0; i < size; ++i) {
            if (file.getName().equals(tmp.get(i).getName()) &&
                    file.getSize().equals(tmp.get(i).getSize()) &&
                    file.getExtension().equals(tmp.get(i).getExtension())) {
                Util.mRemoteFileList.get(i).addMacAddress(mac);
                return false;
            }
        }
        return true;
    }

    private List<String> getMacList(JSONObject jsonObject) {
        JSONArray arr;
        List<String> list = new ArrayList<>();
        try {
            arr = new JSONArray(jsonObject);
            for (int i = 0; i < arr.length(); i++) {
                list.add(arr.getJSONObject(i).getString("name"));
            }
        } catch (JSONException e) {
            Log.e(JSON_TAG, "Invalid json");
        }
        return list;
    }

    private void writeJsonInFile(String content, String filePath) {
        File root = mContext.getFilesDir();
        File file = new File(root, filePath);
        Util.mHomeFilePath = file.getAbsolutePath();
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.write(content);
            myOutWriter.close();
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            String JSON_WRITE_TAG = "Write failed";
            Log.e(JSON_WRITE_TAG, "File write failed: " + e.toString());
        }
    }

    @Nullable
    private List<String> getFileNames() {
        File fileDir = new File("remoteFiles");
        if (!fileDir.exists() || !fileDir.isDirectory()) {
            return null;
        }
        List<String> names = new ArrayList<>(Arrays.asList(fileDir.list()));
        String homeFileName = Util.mNickname + "files.json";
        String remoteFileName = "remotefiles.json";
        names.remove(homeFileName);
        names.remove(remoteFileName);
        if (names.isEmpty()) {
            return null;
        }
        return names;
    }
}
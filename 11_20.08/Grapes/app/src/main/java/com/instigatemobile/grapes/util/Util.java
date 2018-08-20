package com.instigatemobile.grapes.util;

import android.util.Log;

import com.codekidlabs.storagechooser.StorageChooser;
import com.instigatemobile.grapes.fragments.HomeFragment;
import com.instigatemobile.grapes.models.ActiveNodesModel;
import com.instigatemobile.grapes.models.FileModel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class Util {
    public static final String IS_FIRST_RUN = "isFirstRun";
    public static final String NICKNAME = "nickname";
    public static final String NAME = "name";
    public static final String ICON = "icon";
    public static final String SIZE = "size";
    public static final String EXTENSION = "extension";
    public static final String DATE = "date";
    public static final String PATH = "path";
    public static final String FILE_SIZE = "file size";
    public static final String MAC = "macAddr";
    public static final String REMOTE_FILE_NAME = "remotefiles.json";
    public static String HOME_FILE_NAME = "files.json";
    public static List<FileModel> mHomeFileList = new ArrayList<>();
    public static List<FileModel> mRemoteFileList = new ArrayList<>();
    public static List<FileModel> mBeforeSearchList;
    public static List<FileModel> mCurrentList;
    public static boolean isHomeFragment = true;
    public static int homeCategorie;
    public static int remoteCategorie;
    public static int homeSort;
    public static int remoteSort;
    public static StorageChooser.Builder mBuilder;
    public static String mHomeFilePath;
    public static String mRemoteFilePath;
    public static String mNickname;
    public static List<ActiveNodesModel> mNodes = new ArrayList<>();

    public static void filter(String... ext) {
        if (isHomeFragment) {
            mCurrentList = mHomeFileList;
        } else {
            mCurrentList = mRemoteFileList;
        }
        List<FileModel> filteredList = new ArrayList<>();
        for (FileModel model : mCurrentList) {
            for (String tmpExt : ext) {
                if (tmpExt.equals(model.getExtension())) {
                    filteredList.add(model);
                    break;
                }
            }
        }
        mCurrentList = filteredList;

    }

    public static void sortBy(String sortBy) {
        if (isHomeFragment) {
            mCurrentList = mHomeFileList;
        } else {
            mCurrentList = mRemoteFileList;
        }
        switch (sortBy.toLowerCase()) {
            case NAME:
                Collections.sort(mCurrentList, new Comparator<FileModel>() {
                    @Override
                    public int compare(FileModel o1, FileModel o2) {
                        return (o1.getName()).compareToIgnoreCase(o2.getName());
                    }
                });
                break;
            case DATE:
                Collections.sort(mCurrentList, new Comparator<FileModel>() {
                    @Override
                    public int compare(FileModel o1, FileModel o2) {
                        return (o1.getDate()).compareToIgnoreCase(o2.getDate());
                    }
                });
                break;
            case FILE_SIZE:
                Collections.sort(mCurrentList, new Comparator<FileModel>() {
                    @Override
                    public int compare(FileModel o1, FileModel o2) {
                        return String.valueOf((o1.getSize())).compareTo(String.valueOf(o2.getSize()));
                    }
                });
                break;
        }
        mBeforeSearchList = mCurrentList;
    }

    public static void search(CharSequence charSequence) {
        String charString = charSequence.toString();
        mCurrentList = new ArrayList<>();
        for (FileModel file : mBeforeSearchList) {
            if (file.getName().toLowerCase().contains(charString.toLowerCase())) {
                mCurrentList.add(file);
            }
        }

    }

    public static boolean copyFile(InputStream inputStream, OutputStream out) {
        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                out.write(buf, 0, len);

            }
            out.close();
            inputStream.close();
        } catch (IOException e) {
            Log.d(TAG, e.toString());
            return false;
        }
        return true;
    }
}

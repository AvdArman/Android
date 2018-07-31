package com.example.arman.userlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Data {
    public static List<User> userList = new ArrayList<>();
    private static List<String> imageUrlList = new ArrayList<>();

    static void setData() {
        imageUrlList.add("https://images.pexels.com/photos/34950/pexels-photo.jpg?auto=compress&cs=tinysrgb&h=350");
        imageUrlList.add("https://www.w3schools.com/w3css/img_lights.jpg");
        imageUrlList.add("http://wowslider.com/sliders/demo-18/data1/images/hongkong1081704.jpg");
        imageUrlList.add("http://all4desktop.com/data_images/original/4237670-images.jpg");
        imageUrlList.add("https://www.istockphoto.com/resources/images/PhotoFTLP/img_67920257.jpg");
        imageUrlList.add("https://www.bmwjamaica.com/content/dam/bmw/common/all-models/4-series/gran-coupe/2017/images-and-videos/images/BMW-4-series-gran-coupe-images-and-videos-img-890x501-01.jpg/_jcr_content/renditions/cq5dam.resized.img.890.medium.time1487328154325.jpg");
        imageUrlList.add("https://www.bmwjamaica.com/content/dam/bmw/common/all-models/4-series/gran-coupe/2017/images-and-videos/images/BMW-4-series-gran-coupe-images-and-videos-img-890x501-01.jpg/_jcr_content/renditions/cq5dam.resized.img.890.medium.time1487328154325.jpg");
        imageUrlList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQhjG6SqQXoBXEl0UZSzRuY-XaNPoSebZfVb9kKsA3q31PKBxcAvw");
        user2();
        user3();
        user4();
        user5();
        user4();
        user3();
        user1();
        user2();
        user4();
        user2();
        user1();
        user5();
    }

    static void addRandom() {
        Random r = new Random();
        int id = r.nextInt(5 - 1) + 1;
        User user = null;
        switch (id) {
            case 1:
                user1();
                break;
            case 2:
                user2();
                break;
            case 3:
                user3();
                break;
            case 4:
                user4();
                break;
            case 5:
                user5();
                break;
        }
    }

    static void remove(int position) {
        if (position < userList.size()) {
            userList.remove(position);
        }
    }

    static void user1() {
        User user = new User("Arman Avdalyan", "077993615", "avdalyan1999@gmail.com",
                "Description", imageUrlList);
        userList.add(user);
    }

    static void user2() {
        User user = new User("Armen Gabrielyan", "077993615", "avdalyan1999@gmail.com",
                "Description", imageUrlList);
        userList.add(user);
    }

    static void user3() {
        User user = new User("Karen Ghambaryan", "077993615", "avdalyan1999@gmail.com",
                "Description", imageUrlList);
        userList.add(user);
    }

    static void user4() {
        User user = new User("Anna Sargsyan", "077993615", "avdalyan1999@gmail.com",
                "Description", imageUrlList);
        userList.add(user);
    }

    static void user5() {
        User user = new User("Ashot Saribekyan", "077993615", "avdalyan1999@gmail.com",
                "Description", imageUrlList);
        userList.add(user);
    }
}
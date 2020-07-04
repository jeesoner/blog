package com.cijee.blog.util;

import java.io.File;

/**
 * @author cijee
 * @date 2020/7/4
 */
public class AvatarUtils {

    private static final String suffix = ".jpg";
    private static final String prefix = "/images/avatar/";
    private static final int length = 4; // /images/avatar下的图片数量

    /**
     * 从/images/avatar下随机取图片作为头像
     * @return
     */
    public static String getRandomAvatar() {
        return prefix + (int) (Math.random() * length + 1) + suffix;
    }

}

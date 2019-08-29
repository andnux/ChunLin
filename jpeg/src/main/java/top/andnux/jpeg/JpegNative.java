package top.andnux.jpeg;

import android.graphics.Bitmap;

public class JpegNative {

    static {
        System.loadLibrary("jpeg");
    }
    /**
     * 调用JPEG进行图像压缩
     * @param bitmap 图片
     * @param quality 质量
     * @param path 输出路径
     * @param optimize 是否使用哈夫曼算法
     * @return
     */
    public static native int compress(Bitmap bitmap, int quality,
                                      String path, boolean optimize) ;
}

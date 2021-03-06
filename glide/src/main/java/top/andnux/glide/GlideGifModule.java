package top.andnux.glide;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

/**
 * Created by 张春林 on 2018\11\18 0018.
 */

@GlideModule
public class GlideGifModule extends AppGlideModule {

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        //第一个参数任意填写
        registry.prepend(Registry.BUCKET_GIF, InputStream.class, FrameSequenceDrawable.class, new FrameSequenceDecoder(glide.getBitmapPool()));
    }
}

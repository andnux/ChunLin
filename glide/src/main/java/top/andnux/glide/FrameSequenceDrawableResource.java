package top.andnux.glide;

import android.support.rastermill.FrameSequenceDrawable;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.resource.drawable.DrawableResource;

/**
 * Created by Administrator on 2018\11\18 0018.
 */

public class FrameSequenceDrawableResource extends DrawableResource<FrameSequenceDrawable> {

    FrameSequenceDrawableResource(FrameSequenceDrawable drawable) {
        super(drawable);
    }

    @NonNull
    @Override
    public Class<FrameSequenceDrawable> getResourceClass() {
        return FrameSequenceDrawable.class;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public void recycle() {
        drawable.stop();
        drawable.destroy();
    }
}

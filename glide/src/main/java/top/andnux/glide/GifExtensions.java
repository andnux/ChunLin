package top.andnux.glide;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideType;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by Administrator on 2018\11\19 0019.
 */

@GlideExtension
public class GifExtensions {
    private GifExtensions() {
    }

    final static RequestOptions DECODE_TYPE = RequestOptions
            .decodeTypeOf(FrameSequenceDrawable.class)
            .lock();

    @GlideType(FrameSequenceDrawable.class)
    public static RequestBuilder<FrameSequenceDrawable> asGif2(
            RequestBuilder<FrameSequenceDrawable> requestBuilder) {
        return requestBuilder.apply(DECODE_TYPE);
    }
}

package com.bumptech.glide;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;


import java.lang.Class;
import java.lang.Override;
import java.util.Collections;
import java.util.Set;
import top.andnux.glide.GlideGifModule;

final class GeneratedAppGlideModuleImpl extends GeneratedAppGlideModule {

  private final GlideGifModule appGlideModule;

  GeneratedAppGlideModuleImpl() {
    appGlideModule = new GlideGifModule();
    if (Log.isLoggable("Glide", Log.DEBUG)) {
      Log.d("Glide", "Discovered AppGlideModule from annotation: top.andnux.libglide.GlideGifModule");
    }
  }

  @Override
  public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
    appGlideModule.applyOptions(context, builder);
  }

  @Override
  public void registerComponents(@NonNull Context context, @NonNull Glide glide,
      @NonNull Registry registry) {
    appGlideModule.registerComponents(context, glide, registry);
  }

  @Override
  public boolean isManifestParsingEnabled() {
    return appGlideModule.isManifestParsingEnabled();
  }

  @Override
  @NonNull
  public Set<Class<?>> getExcludedModuleClasses() {
    return Collections.emptySet();
  }

  @Override
  @NonNull
  GeneratedRequestManagerFactory getRequestManagerFactory() {
    return new GeneratedRequestManagerFactory();
  }
}

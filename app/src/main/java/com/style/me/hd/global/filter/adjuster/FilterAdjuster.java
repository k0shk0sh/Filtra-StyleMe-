package com.style.me.hd.global.filter.adjuster;

import com.style.me.hd.global.helper.AdjusterHelper;

import jp.co.cyberagent.android.gpuimage.GPUImage3x3TextureSamplingFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageDissolveBlendFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageEmbossFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageExposureFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGammaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHighlightShadowFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHueFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageMonochromeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageOpacityFilter;
import jp.co.cyberagent.android.gpuimage.GPUImagePixelationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImagePosterizeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageRGBFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSaturationFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSepiaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSharpenFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSobelEdgeDetection;
import jp.co.cyberagent.android.gpuimage.GPUImageVignetteFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageWhiteBalanceFilter;

/**
 * Created by Kosh on 10/11/2015. copyrights are reserved
 */
public class FilterAdjuster {

    private final AdjusterHelper<? extends GPUImageFilter> adjuster;

    public FilterAdjuster(final GPUImageFilter filter) {
        if (filter instanceof GPUImageSharpenFilter) {
            adjuster = new SharpnessAdjuster().filter(filter);
        } else if (filter instanceof GPUImageSepiaFilter) {
            adjuster = new SepiaAdjuster().filter(filter);
        } else if (filter instanceof GPUImageContrastFilter) {
            adjuster = new ContrastAdjuster().filter(filter);
        } else if (filter instanceof GPUImageGammaFilter) {
            adjuster = new GammaAdjuster().filter(filter);
        } else if (filter instanceof GPUImageBrightnessFilter) {
            adjuster = new BrightnessAdjuster().filter(filter);
        } else if (filter instanceof GPUImageSobelEdgeDetection) {
            adjuster = new SobelAdjuster().filter(filter);
        } else if (filter instanceof GPUImage3x3TextureSamplingFilter) {
            adjuster = new GPU3x3TextureAdjuster().filter(filter);
        } else if (filter instanceof GPUImageEmbossFilter) {
            adjuster = new EmbossAdjuster().filter(filter);
        } else if (filter instanceof GPUImageHueFilter) {
            adjuster = new HueAdjuster().filter(filter);
        } else if (filter instanceof GPUImagePosterizeFilter) {
            adjuster = new PosterizeAdjuster().filter(filter);
        } else if (filter instanceof GPUImagePixelationFilter) {
            adjuster = new PixelationAdjuster().filter(filter);
        } else if (filter instanceof GPUImageSaturationFilter) {
            adjuster = new SaturationAdjuster().filter(filter);
        } else if (filter instanceof GPUImageExposureFilter) {
            adjuster = new ExposureAdjuster().filter(filter);
        } else if (filter instanceof GPUImageHighlightShadowFilter) {
            adjuster = new HighlightShadowAdjuster().filter(filter);
        } else if (filter instanceof GPUImageMonochromeFilter) {
            adjuster = new MonochromeAdjuster().filter(filter);
        } else if (filter instanceof GPUImageOpacityFilter) {
            adjuster = new OpacityAdjuster().filter(filter);
        } else if (filter instanceof GPUImageRGBFilter) {
            adjuster = new RedAdjuster().filter(filter);
        } else if (filter instanceof GPUImageWhiteBalanceFilter) {
            adjuster = new WhiteBalanceAdjuster().filter(filter);
        } else if (filter instanceof GPUImageVignetteFilter) {
            adjuster = new VignetteAdjuster().filter(filter);
        } else if (filter instanceof GPUImageDissolveBlendFilter) {
            adjuster = new DissolveBlendAdjuster().filter(filter);
        } else {
            adjuster = null;
        }
    }

    public void adjust(final int percentage) {
        if (adjuster != null) {
            adjuster.adjust(percentage);
        }
    }
}

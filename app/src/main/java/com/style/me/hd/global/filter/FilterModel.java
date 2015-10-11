package com.style.me.hd.global.filter;

import android.content.Context;

import com.style.me.hd.global.helper.AdjusterHelper;

import java.util.ArrayList;
import java.util.List;

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
public class FilterModel {

    private GPUImageFilter filter;
    private boolean adjustable;
    private Context context;
    private AdjusterHelper adjusterHelper;
    private List<FilterModel> filters = new ArrayList<>();

    public FilterModel(Context context) {
        this.context = context;
        getFilters().addAll(instaFilters());
        getFilters().addAll(adjustableFilters());
    }

    public FilterModel() {}

    private List<FilterModel> instaFilters() {
        List<FilterModel> filterModels = new ArrayList<>();
        FilterModel insta = new FilterModel();
        insta.setFilter(new IF1977Filter(context));
        filterModels.add(insta);
        insta = new FilterModel();
        insta.setFilter(new IFAmaroFilter(context));
        filterModels.add(insta);
        insta = new FilterModel();
        insta.setFilter(new IFBrannanFilter(context));
        filterModels.add(insta);
        insta = new FilterModel();
        insta.setFilter(new IFEarlybirdFilter(context));
        filterModels.add(insta);
        insta = new FilterModel();
        insta.setFilter(new IFHefeFilter(context));
        filterModels.add(insta);
        insta = new FilterModel();
        insta.setFilter(new IFHudsonFilter(context));
        filterModels.add(insta);
        insta = new FilterModel();
        insta.setFilter(new IFInkwellFilter(context));
        filterModels.add(insta);
        insta = new FilterModel();
        insta.setFilter(new IFLomoFilter(context));
        filterModels.add(insta);
        insta = new FilterModel();
        insta.setFilter(new IFLordKelvinFilter(context));
        filterModels.add(insta);
        insta = new FilterModel();
        insta.setFilter(new IFNashvilleFilter(context));
        filterModels.add(insta);
        insta = new FilterModel();
        insta.setFilter(new IFRiseFilter(context));
        filterModels.add(insta);
        insta = new FilterModel();
        insta.setFilter(new IFSierraFilter(context));
        filterModels.add(insta);
        insta = new FilterModel();
        insta.setFilter(new IFSutroFilter(context));
        filterModels.add(insta);
        insta = new FilterModel();
        insta.setFilter(new IFToasterFilter(context));
        filterModels.add(insta);
        insta = new FilterModel();
        insta.setFilter(new IFValenciaFilter(context));
        filterModels.add(insta);
        insta = new FilterModel();
        insta.setFilter(new IFWaldenFilter(context));
        filterModels.add(insta);
        insta = new FilterModel();
        insta.setFilter(new IFXprollFilter(context));
        filterModels.add(insta);
        return filterModels;
    }

    private List<FilterModel> adjustableFilters() {
        List<FilterModel> filterModels = new ArrayList<>();
        FilterModel adjust = new FilterModel();
        adjust.setAdjustable(true);
        adjust.setFilter(new GPUImageRGBFilter());
        filterModels.add(adjust);
        adjust = new FilterModel();
        adjust.setAdjustable(true);
        adjust.setFilter(new GPUImageBrightnessFilter());
        filterModels.add(adjust);
        adjust = new FilterModel();
        adjust.setAdjustable(true);
        adjust.setFilter(new GPUImageContrastFilter());
        filterModels.add(adjust);
        adjust = new FilterModel();
        adjust.setAdjustable(true);
        adjust.setFilter(new GPUImageDissolveBlendFilter());
        filterModels.add(adjust);
        adjust = new FilterModel();
        adjust.setAdjustable(true);
        adjust.setFilter(new GPUImageEmbossFilter());
        filterModels.add(adjust);
        adjust = new FilterModel();
        adjust.setAdjustable(true);
        adjust.setFilter(new GPUImageExposureFilter());
        filterModels.add(adjust);
        adjust = new FilterModel();
        adjust.setAdjustable(true);
        adjust.setFilter(new GPUImageGammaFilter());
        filterModels.add(adjust);
        adjust = new FilterModel();
        adjust.setAdjustable(true);
        adjust.setFilter(new GPUImage3x3TextureSamplingFilter());
        filterModels.add(adjust);
        adjust = new FilterModel();
        adjust.setAdjustable(true);
        adjust.setFilter(new GPUImageHighlightShadowFilter());
        filterModels.add(adjust);
        adjust = new FilterModel();
        adjust.setAdjustable(true);
        adjust.setFilter(new GPUImageHueFilter());
        filterModels.add(adjust);
        adjust = new FilterModel();
        adjust.setAdjustable(true);
        adjust.setFilter(new GPUImageMonochromeFilter());
        filterModels.add(adjust);
        adjust = new FilterModel();
        adjust.setAdjustable(true);
        adjust.setFilter(new GPUImageOpacityFilter());
        filterModels.add(adjust);
        adjust = new FilterModel();
        adjust.setAdjustable(true);
        adjust.setFilter(new GPUImagePixelationFilter());
        filterModels.add(adjust);
        adjust = new FilterModel();
        adjust.setAdjustable(true);
        adjust.setFilter(new GPUImagePosterizeFilter());
        filterModels.add(adjust);
        adjust = new FilterModel();
        adjust.setAdjustable(true);
        adjust.setFilter(new GPUImageSaturationFilter());
        filterModels.add(adjust);
        adjust = new FilterModel();
        adjust.setAdjustable(true);
        adjust.setFilter(new GPUImageSepiaFilter());
        filterModels.add(adjust);
        adjust = new FilterModel();
        adjust.setAdjustable(true);
        adjust.setFilter(new GPUImageSharpenFilter());
        filterModels.add(adjust);
        adjust = new FilterModel();
        adjust.setAdjustable(true);
        adjust.setFilter(new GPUImageSobelEdgeDetection());
        filterModels.add(adjust);
        adjust = new FilterModel();
        adjust.setAdjustable(true);
        adjust.setFilter(new GPUImageVignetteFilter());
        filterModels.add(adjust);
        adjust = new FilterModel();
        adjust.setAdjustable(true);
        adjust.setFilter(new GPUImageWhiteBalanceFilter());
        filterModels.add(adjust);
        return filterModels;
    }

    public GPUImageFilter getFilter() {
        return filter;
    }

    public void setFilter(GPUImageFilter filter) {
        this.filter = filter;
    }

    public boolean isAdjustable() {
        return adjustable;
    }

    public void setAdjustable(boolean adjustable) {
        this.adjustable = adjustable;
    }

    public List<FilterModel> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterModel> filters) {
        this.filters = filters;
    }

    public AdjusterHelper getAdjusterHelper() {
        return adjusterHelper;
    }

    public void setAdjusterHelper(AdjusterHelper adjusterHelper) {
        this.adjusterHelper = adjusterHelper;
    }
}

package com.plusls.ommc.impl.feature.worldEaterMineHelper;

import com.plusls.ommc.SharedConstants;
import top.hendrixshen.magiclib.api.malilib.config.option.EnumOptionEntry;

public enum WorldEaterMineHelperRenderAxisType implements EnumOptionEntry {
    X,
    Y,
    Z;

    @Override
    public EnumOptionEntry[] getAllValues() {
        return WorldEaterMineHelperRenderAxisType.values();
    }

    @Override
    public EnumOptionEntry getDefault() {
        return X;
    }

    @Override
    public String getTranslationPrefix() {
        return String.format("%s.config.option.worldEaterMineHelperRenderAxis", SharedConstants.getModIdentifier());
    }
}
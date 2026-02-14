package com.plusls.ommc.mixin.accessor;

//#if MC > 12006
import net.fabricmc.fabric.impl.client.indigo.renderer.render.BlockRenderInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = BlockRenderInfo.class, remap = false)
public interface AccessorBlockRenderInfo {
    @Accessor
    boolean getEnableCulling();

    @Accessor
    void setEnableCulling(boolean enableCulling);
}
//#else
//$$ import org.spongepowered.asm.mixin.Mixin;
//$$ import top.hendrixshen.magiclib.api.preprocess.DummyClass;
//$$
//$$ @Mixin(value = DummyClass.class)
//$$ public interface AccessorBlockRenderInfo {
//$$ }
//#endif

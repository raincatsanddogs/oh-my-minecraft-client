package com.plusls.ommc.mixin.accessor;

//#if MC > 12006
import net.fabricmc.fabric.impl.client.indigo.renderer.render.AbstractTerrainRenderContext;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.BlockRenderInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = AbstractTerrainRenderContext.class, remap = false)
public interface AccessorAbstractTerrainRenderContext {
    @Accessor
    BlockRenderInfo getBlockInfo();
}
//#else
//$$ import org.spongepowered.asm.mixin.Mixin;
//$$ import top.hendrixshen.magiclib.api.preprocess.DummyClass;
//$$
//$$ @Mixin(value = DummyClass.class)
//$$ public interface AccessorAbstractTerrainRenderContext {
//$$ }
//#endif

package com.plusls.ommc.mixin.feature.worldEaterMineHelper.fabric;

//#if MC <= 12101
import com.plusls.ommc.impl.feature.worldEaterMineHelper.WorldEaterMineHelper;
import net.fabricmc.fabric.impl.client.indigo.renderer.render.TerrainRenderContext;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

//#if MC > 11903
import net.fabricmc.fabric.impl.client.indigo.renderer.render.AbstractBlockRenderContext;
//#else
//$$ import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
//$$ import org.spongepowered.asm.mixin.Final;
//$$ import org.spongepowered.asm.mixin.Shadow;
//$$
//#if MC > 11701
//$$ import net.fabricmc.fabric.impl.client.indigo.renderer.render.BlockRenderInfo;
//#else
//$$ import net.fabricmc.fabric.impl.client.indigo.renderer.render.TerrainBlockRenderInfo;
//#endif
//#endif

//#if MC > 11802
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//#else
//$$ import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//#endif

//#if MC > 11701
//#else
//$$ import net.fabricmc.fabric.impl.client.indigo.renderer.render.TerrainBlockRenderInfo;
//#endif

//#if MC > 11404
import com.mojang.blaze3d.vertex.PoseStack;
//#endif

@SuppressWarnings("UnstableApiUsage")
@Mixin(value = TerrainRenderContext.class, remap = false)
public abstract class MixinTerrainRenderContext
        //#if MC > 11903
        extends AbstractBlockRenderContext
        //#else
        //$$ implements RenderContext
        //#endif
{
    //#if MC < 11904
    //$$ @Shadow
    //$$ @Final
    //#if MC > 11701
    //$$ private BlockRenderInfo blockInfo;
    //#else
    //$$ private TerrainBlockRenderInfo blockInfo;
    //#endif
    //#endif

    @Dynamic
    @Inject(
            method = {
                    "tessellateBlock", // For fabric-renderer-indigo 0.5.0 and above
                    "tesselateBlock" // For fabric-renderer-indigo 0.5.0 below
            },
            at = @At(value = "INVOKE",
                    //#if MC > 11903
                    target = "Lnet/minecraft/client/resources/model/BakedModel;emitBlockQuads(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Ljava/util/function/Supplier;Lnet/fabricmc/fabric/api/renderer/v1/render/RenderContext;)V",
                    //#else
                    //$$ target = "Lnet/fabricmc/fabric/api/renderer/v1/model/FabricBakedModel;emitBlockQuads(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Ljava/util/function/Supplier;Lnet/fabricmc/fabric/api/renderer/v1/render/RenderContext;)V",
                    //#endif
                    shift = At.Shift.AFTER, ordinal = 0, remap = true))
    private void emitCustomBlockQuads(BlockState blockState, BlockPos blockPos, BakedModel model,
                                      //#if MC > 11404
                                      PoseStack matrixStack,
                                      //#endif
                                      //#if MC > 11802
                                      CallbackInfo ci) {
        //#else
        //$$ CallbackInfoReturnable<Boolean> cir) {
        //#endif
        WorldEaterMineHelper.emitCustomBlockQuads(blockInfo.blockView, blockInfo.blockState, blockInfo.blockPos, blockInfo.randomSupplier, this);
    }
}
//#else
//$$ import com.plusls.ommc.game.Configs;
//$$ import com.plusls.ommc.mixin.accessor.AccessorAbstractTerrainRenderContext;
//$$ import com.plusls.ommc.impl.feature.worldEaterMineHelper.WorldEaterMineHelper;
//$$ import com.plusls.ommc.impl.feature.worldEaterMineHelper.WorldEaterMineHelperRenderAxisType;
//$$ import com.plusls.ommc.mixin.accessor.AccessorBlockRenderInfo;
//$$ import com.plusls.ommc.mixin.accessor.AccessorBlockStateBase;
//$$ import com.mojang.blaze3d.vertex.PoseStack;
//$$ import com.mojang.math.Axis;
//$$ import net.fabricmc.fabric.impl.client.indigo.renderer.render.BlockRenderInfo;
//$$ import net.fabricmc.fabric.impl.client.indigo.renderer.render.TerrainRenderContext;
//$$ import net.minecraft.client.renderer.block.model.BlockStateModel;
//$$ import net.minecraft.core.BlockPos;
//$$ import net.minecraft.world.level.block.state.BlockState;
//$$ import net.minecraft.util.Mth;
//$$ import org.spongepowered.asm.mixin.Mixin;
//$$ import org.spongepowered.asm.mixin.Shadow;
//$$ import org.spongepowered.asm.mixin.Unique;
//$$ import org.spongepowered.asm.mixin.injection.At;
//$$ import org.spongepowered.asm.mixin.injection.Inject;
//$$ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//$$
//$$ @Mixin(value = TerrainRenderContext.class, remap = false)
//$$ public abstract class MixinTerrainRenderContext {
//$$     @Unique
//$$     private static final ThreadLocal<Integer> ommc$originalLuminance = ThreadLocal.withInitial(() -> -1);
//$$     @Unique
//$$     private static final ThreadLocal<Boolean> ommc$renderHintTag = ThreadLocal.withInitial(() -> false);
//$$
//$$     @Shadow
//$$     private PoseStack matrixStack;
//$$
//$$     @Shadow
//$$     public abstract void bufferModel(BlockStateModel model, BlockState blockState, BlockPos blockPos);
//$$
//$$     @Inject(method = "bufferModel", at = @At("HEAD"))
//$$     private void ommc$preBufferModel(BlockStateModel model, BlockState blockState, BlockPos blockPos, CallbackInfo ci) {
//$$         if (WorldEaterMineHelper.shouldUseCustomModel(blockState, blockPos)) {
//$$             int originalLuminance = blockState.getLightEmission();
//$$             this.ommc$originalLuminance.set(originalLuminance);
//$$             ((AccessorBlockStateBase) blockState).setLightEmission(15);
//$$         }
//$$     }
//$$
//$$     @Inject(method = "bufferModel", at = @At("RETURN"))
//$$     private void ommc$postBufferModel(BlockStateModel model, BlockState blockState, BlockPos blockPos, CallbackInfo ci) {
//$$         int originalLuminance = this.ommc$originalLuminance.get();
//$$
//$$         if (originalLuminance != -1) {
//$$             ((AccessorBlockStateBase) blockState).setLightEmission(originalLuminance);
//$$             this.ommc$originalLuminance.set(-1);
//$$         }
//$$
//$$         if (!this.ommc$renderHintTag.get() && WorldEaterMineHelper.shouldUseCustomModel(blockState, blockPos)) {
//$$             this.ommc$renderHintTag.set(true);
//$$             int renderOffset = Mth.clamp(Configs.worldEaterMineHelperRenderOffset.getIntegerValue(), 0, 15);
//$$             WorldEaterMineHelperRenderAxisType axis = (WorldEaterMineHelperRenderAxisType) Configs.worldEaterMineHelperRenderAxis.getOptionListValue();
//$$             double localCenterX = (blockPos.getX() & 15) + 0.5D;
//$$             double localCenterY = (blockPos.getY() & 15) + 0.5D;
//$$             double localCenterZ = (blockPos.getZ() & 15) + 0.5D;
//$$             this.matrixStack.pushPose();
//$$             this.matrixStack.translate(0.0, renderOffset, 0.0);
//$$             this.matrixStack.translate(localCenterX, localCenterY, localCenterZ);
//$$             if (axis == WorldEaterMineHelperRenderAxisType.Y) {
//$$                 this.matrixStack.mulPose(Axis.YP.rotationDegrees(45.0F));
//$$             } else if (axis == WorldEaterMineHelperRenderAxisType.Z) {
//$$                 this.matrixStack.mulPose(Axis.ZP.rotationDegrees(45.0F));
//$$             } else {
//$$                 this.matrixStack.mulPose(Axis.XP.rotationDegrees(45.0F));
//$$             }
//$$             this.matrixStack.translate(-localCenterX, -localCenterY, -localCenterZ);
//$$             BlockRenderInfo blockRenderInfo = ((AccessorAbstractTerrainRenderContext) this).getBlockInfo();
//$$             AccessorBlockRenderInfo accessorBlockRenderInfo = (AccessorBlockRenderInfo) blockRenderInfo;
//$$             boolean oldEnableCulling = accessorBlockRenderInfo.getEnableCulling();
//$$             accessorBlockRenderInfo.setEnableCulling(false);
//$$             this.bufferModel(model, blockState, blockPos);
//$$             accessorBlockRenderInfo.setEnableCulling(oldEnableCulling);
//$$             this.matrixStack.popPose();
//$$             this.ommc$renderHintTag.set(false);
//$$         }
//$$     }
//$$ }
//#endif

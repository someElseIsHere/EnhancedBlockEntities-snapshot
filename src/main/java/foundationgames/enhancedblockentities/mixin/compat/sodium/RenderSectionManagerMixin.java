package foundationgames.enhancedblockentities.mixin.compat.sodium;

import foundationgames.enhancedblockentities.util.WorldUtil;
import foundationgames.enhancedblockentities.util.duck.ChunkRebuildTaskAccess;
import net.caffeinemc.mods.sodium.client.render.chunk.RenderSection;
import net.caffeinemc.mods.sodium.client.render.chunk.RenderSectionManager;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.tasks.ChunkBuilderMeshingTask;
import net.minecraft.util.math.ChunkSectionPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * <p>Adapted from {@link foundationgames.enhancedblockentities.mixin.WorldRendererMixin}</p>
 */
@Pseudo
@Mixin(value = RenderSectionManager.class, remap = false)
public class RenderSectionManagerMixin {
    @ModifyVariable(
            method = "submitSectionTasks(Lnet/caffeinemc/mods/sodium/client/render/chunk/compile/executor/ChunkJobCollector;Lnet/caffeinemc/mods/sodium/client/render/chunk/ChunkUpdateType;Z)V",
            at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/render/chunk/RenderSection;isDisposed()Z")
    )
    private RenderSection enhanced_bes$compat_sodium$cacheUpdatingChunk(RenderSection section) {
        if (!WorldUtil.CHUNK_UPDATE_TASKS.isEmpty()) {
            var pos = ChunkSectionPos.from(section.getChunkX(), section.getChunkY(), section.getChunkZ());

            if (WorldUtil.CHUNK_UPDATE_TASKS.containsKey(pos)) {
                var task = WorldUtil.CHUNK_UPDATE_TASKS.remove(pos);
                ((ChunkRebuildTaskAccess) section).enhanced_bes$setTaskAfterRebuild(task);
            }
        }

        return section;
    }

    @Inject(method = "createRebuildTask", at = @At(value = "RETURN"))
    private void enhanced_bes$runPostRebuildTask(RenderSection render, int frame, CallbackInfoReturnable<ChunkBuilderMeshingTask> cir) {
        ((ChunkRebuildTaskAccess) render).enhanced_bes$runAfterRebuildTask();
    }
}

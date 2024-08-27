package foundationgames.enhancedblockentities.mixin;

import foundationgames.enhancedblockentities.util.duck.BakedModelManagerAccess;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(BakedModelManager.class)
public class BakedModelManagerMixin implements BakedModelManagerAccess {

    @Shadow private Map<ModelIdentifier, BakedModel> models;

    @Override
    public BakedModel enhanced_bes$getModel(Identifier id) {
        for (Map.Entry<ModelIdentifier, BakedModel> entry : this.models.entrySet()) {
            if (entry.getKey().id().equals(id)) return entry.getValue();
        }
        return null;
    }
}

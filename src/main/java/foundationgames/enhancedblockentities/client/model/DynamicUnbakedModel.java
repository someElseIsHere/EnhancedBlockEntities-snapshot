package foundationgames.enhancedblockentities.client.model;

import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.Baker;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class DynamicUnbakedModel implements UnbakedModel {
    private final Identifier[] models;
    private final ModelSelector selector;
    private final DynamicModelEffects effects;

    public DynamicUnbakedModel(Identifier[] models, ModelSelector selector, DynamicModelEffects effects) {
        this.models = models;
        this.selector = selector;
        this.effects = effects;
    }

    @Override
    public void resolve(Resolver resolver, ModelType currentlyResolvingType) {}

    @Nullable
    @Override
    public BakedModel bake(Baker baker, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer) {
        BakedModel[] baked = new BakedModel[models.length];
        for (int i = 0; i < models.length; i++) {
            baked[i] = baker.getModel(models[i]).bake(baker, textureGetter, rotationContainer);
        }
        return new DynamicBakedModel(baked, selector, effects);
    }
}

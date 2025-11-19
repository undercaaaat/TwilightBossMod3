package com.example.twilightbossaddon.client;

import com.example.twilightbossaddon.TwilightBossAddon;
import com.example.twilightbossaddon.entity.NarratorEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidRenderer; // !! 导入 HumanoidRenderer !!
import net.minecraft.resources.ResourceLocation;

/**
 * 这是Boss的渲染器类（贴图）
 * 它告诉游戏使用哪个模型 (NarratorModel) 和哪张贴图
 */
public class NarratorRenderer extends HumanoidRenderer<NarratorEntity, NarratorModel<NarratorEntity>> {

    /**
     * 定义贴图文件的位置
     */
    private static final ResourceLocation NARRATOR_TEXTURE =
            new ResourceLocation(TwilightBossAddon.MODID, "textures/entity/narrator.png");

    public NarratorRenderer(EntityRendererProvider.Context pContext) {
        // 使用我们的人形模型 (NarratorModel)
        super(pContext, new NarratorModel<>(pContext.bakeLayer(NarratorModelLayers.NARRATOR_LAYER)), 0.5F); // 0.5F 是阴影大小
    }

    /**
     * 返回Boss的贴图
     */
    @Override
    public ResourceLocation getTextureLocation(NarratorEntity pEntity) {
        return NARRATOR_TEXTURE;
    }
}
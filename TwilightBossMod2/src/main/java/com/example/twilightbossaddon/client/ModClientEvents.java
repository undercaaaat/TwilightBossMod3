package com.example.twilightbossaddon.client;

import com.example.twilightbossaddon.TwilightBossAddon;
import com.example.twilightbossaddon.core.ModEntities; // 导入我们的实体注册
import net.minecraft.client.model.HumanoidModel; // !! 导入 HumanoidModel !!
import net.minecraftforge.api.distmarker.Dist; // !! 导入 Dist !!
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod; // !! 导入 Mod !!

/**
 * 注册所有客户端事件
 * bus = Mod.EventBusSubscriber.Bus.MOD -> 监听Mod事件总线
 * value = Dist.CLIENT -> 这个类只在客户端加载，防止服务端崩溃
 */
@Mod.EventBusSubscriber(modid = TwilightBossAddon.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEvents {
    /**
     * 注册实体渲染器
     */
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        // 告诉游戏，当遇到 NARRATOR 实体时，使用 NarratorRenderer 来渲染
        event.registerEntityRenderer(
                ModEntities.NARRATOR.get(), // 确保 ModEntities 已经被正确导入
                NarratorRenderer::new
        );
    }

    /**
     * 注册模型图层定义
     */
    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        // 这里我们定义了 NARRATOR_LAYER，并告诉它使用原版的人形模型骨架
        event.registerLayerDefinition(
                NarratorModelLayers.NARRATOR_LAYER,
                () -> HumanoidModel.createMesh(HumanoidModel.createHumanoidBodyLayer(), 0.0F)
        );
    }
}
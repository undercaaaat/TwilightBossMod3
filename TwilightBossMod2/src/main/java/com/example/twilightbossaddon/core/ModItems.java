package com.example.twilightbossaddon.core;
import com.example.twilightbossaddon.TwilightBossAddon;
import net.minecraft.ChatFormatting; // !! 注意：导入ChatFormatting
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem; // !! 导入 SpawnEggItem
import net.minecraftforge.common.ForgeSpawnEggItem; // !! 导入 ForgeSpawnEggItem
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nullable;
import java.util.List;

public class ModItems {
    /**
     * 创建一个延迟注册器，它会“挂”在 MC 的物品注册表上
     */
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TwilightBossAddon.MODID);

    /**
     * 注册我们的物品 "灰烬之灯"
     * "ash_light" 是这个物品的注册ID，后面资源文件会用到
     */
    public static final RegistryObject<Item> ASH_LIGHT = ITEMS.register("ash_light",
            () -> new Item(new Item.Properties()
                    // Rarity.EPIC 赋予物品紫色的名字
                    .rarity(Rarity.EPIC)
                    .fireResistant()
            ) {
                // 这个 {} 内部是一个匿名内部类，允许我们重写(override) Item 类的方法

                /**
                 * 当玩家在背包中悬停在物品上时，添加描述文本
                 */
                @Override
                public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
                    // 添加你设计的描述
                    pTooltipComponents.add(Component.translatable("tooltip.twilightbossaddon.ash_light.description1")
                            .withStyle(ChatFormatting.GRAY)); // 灰色字体
                    pTooltipComponents.add(Component.translatable("tooltip.twilightbossaddon.ash_light.description2")
                            .withStyle(ChatFormatting.DARK_PURPLE, ChatFormatting.ITALIC)); // 暗紫色 + 斜体

                    super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
                }

                /**
                 * 让物品在背包中显示附魔光效（模仿神器的效果）
                 */
                @Override
                public boolean isFoil(ItemStack pStack) {
                    return true;
                }
            });

    /**
     * 注册 "讲述者" 的刷怪蛋
     * 0x2E2B24 (深灰) - 主色
     * 0x4A463E (中灰) - 辅色
     * .properties() 将它放入 "MISC" (杂项) 创造模式物品栏
     */
    public static final RegistryObject<SpawnEggItem> NARRATOR_SPAWN_EGG =
            ITEMS.register("narrator_spawn_egg",
                    () -> new ForgeSpawnEggItem(ModEntities.NARRATOR,
                            0x2E2B24, 0x4A463E,
                            new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }


}
package com.example.twilightbossaddon.entity;

import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class NarratorEntity extends Monster {
    /**
     * 为Boss创建一个Boss血条
     * BossEvent.BossBarColor.PURPLE 设为紫色
     * BossEvent.BossBarOverlay.PROGRESS 设为标准分段血条
     */
    private final ServerBossEvent bossEvent =
            new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.PURPLE, BossEvent.BossBarOverlay.PROGRESS);

    /**
     * 实体构造函数
     */
    public NarratorEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    /**
     * 注册实体的基础属性（血量、速度等）
     */
    public static AttributeSupplier.Builder createAttributes() {
        // 根据你的设计文档，一阶段血量为 300
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 300.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D) // 设定一个基础移动速度
                .add(Attributes.FOLLOW_RANGE, 64.0D); // 设定一个很大的追踪范围
    }

    /**
     * 注册实体的AI（行为）
     * 这是一个基础AI，我们后续会用你设计的技能替换它
     */
    @Override
    protected void registerGoals() {
        // AI 优先级 (数字越小，优先级越高)

        // 0: 漂浮（如果Boss在水里）
        this.goalSelector.addGoal(0, new FloatGoal(this));
        // 1: 攻击目标（暂时用近战，后续替换为技能）
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, false));
        // 2: 在区域内游荡
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 0.8D));
        // 3: 看向玩家
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 8.0F));
        // 4: 随机看向四周
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));

        // 目标选择 AI
        // 0: 自动选择最近的玩家为攻击目标
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    // --- Boss 血条管理 ---

    @Override
    public void startSeenByPlayer(ServerPlayer pPlayer) {
        super.startSeenByPlayer(pPlayer);
        // 当玩家看到Boss时，向该玩家显示Boss血条
        this.bossEvent.addPlayer(pPlayer);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer pPlayer) {
        super.stopSeenByPlayer(pPlayer);
        // 当玩家离开时，移除Boss血条
        this.bossEvent.removePlayer(pPlayer);
    }

    @Override
    public void customServerAiStep() {
        super.customServerAiStep();
        // 每tick更新血条的血量
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());
    }
}
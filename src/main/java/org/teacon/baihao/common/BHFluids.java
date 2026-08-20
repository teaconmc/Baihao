package org.teacon.baihao.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.LavaFluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidInteractionRegistry;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.teacon.baihao.Baihao;

/**
 * 新熔岩
 */
@EventBusSubscriber
public final class BHFluids {

    public static final DeferredRegister<FluidType> BAIHAO_FLUID_TYPES =
            DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, Baihao.MODID);
    public static final DeferredRegister<Fluid> BAIHAO_FLUIDS =
            DeferredRegister.create(Registries.FLUID, Baihao.MODID);
    public static final DeferredRegister.Blocks BAIHAO_BLOCKS =
            DeferredRegister.createBlocks(Baihao.MODID);

    public static final DeferredHolder<FluidType, NewLavaFluidType> NEW_LAVA_TYPE =
            BAIHAO_FLUID_TYPES.register("new_lava", NewLavaFluidType::new);

    public static final DeferredHolder<Fluid, NewLavaFluid.Source> NEW_LAVA =
            BAIHAO_FLUIDS.register("new_lava", NewLavaFluid.Source::new);
    public static final DeferredHolder<Fluid, NewLavaFluid.Flowing> FLOWING_NEW_LAVA =
            BAIHAO_FLUIDS.register("flowing_new_lava", NewLavaFluid.Flowing::new);

    public static final DeferredBlock<LiquidBlock> NEW_LAVA_BLOCK = BAIHAO_BLOCKS.registerBlock("new_lava",
            properties -> new LiquidBlock(NEW_LAVA.get(), properties),
            () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.FIRE)
                    .replaceable()
                    .noCollision()
                    .randomTicks()
                    .strength(100.0F)
                    .lightLevel(state -> 15)
                    .pushReaction(PushReaction.DESTROY)
                    .noLootTable()
                    .liquid()
                    .sound(SoundType.EMPTY));

    private BHFluids() {
    }

    public static void register(IEventBus modEventBus) {
        BAIHAO_FLUID_TYPES.register(modEventBus);
        BAIHAO_FLUIDS.register(modEventBus);
        BAIHAO_BLOCKS.register(modEventBus);
    }
    
    public static void registerFluidInteractions(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            FluidInteractionRegistry.addInteraction(NEW_LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                    NeoForgeMod.WATER_TYPE.value(),
                    fluidState -> fluidState.isSource()
                            ? Blocks.OBSIDIAN.defaultBlockState()
                            : Blocks.COBBLESTONE.defaultBlockState()));
            FluidInteractionRegistry.addInteraction(NEW_LAVA_TYPE.get(), new FluidInteractionRegistry.InteractionInformation(
                    (level, pos, neighborPos, fluidState) -> level.getBlockState(pos.below()).is(Blocks.SOUL_SOIL)
                            && level.getBlockState(neighborPos).is(Blocks.BLUE_ICE),
                    Blocks.BASALT.defaultBlockState()));
        });
    }

    public static class NewLavaFluidType extends FluidType {
        NewLavaFluidType() {
            super(FluidType.Properties.create()
                    .descriptionId("fluid.baihao.new_lava")
                    .canSwim(false)
                    .canDrown(false)
                    .pathType(PathType.LAVA)
                    .adjacentPathType(null)
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                    .lightLevel(15)
                    .density(3000)
                    .viscosity(6000)
                    .temperature(1300)
                    .addDripstoneDripping(0.05859375F,
                            net.minecraft.core.particles.ParticleTypes.DRIPPING_DRIPSTONE_LAVA,
                            Blocks.LAVA_CAULDRON,
                            SoundEvents.POINTED_DRIPSTONE_DRIP_LAVA_INTO_CAULDRON));
        }

        @Override
        public boolean canConvertToSource(FluidState state, LevelReader level, BlockPos pos) {
            if (level instanceof ServerLevel serverLevel) {
                return serverLevel.getGameRules().get(net.minecraft.world.level.gamerules.GameRules.LAVA_SOURCE_CONVERSION);
            }
            return super.canConvertToSource(state, level, pos);
        }

        @Override
        public double motionScale(Entity entity) {
            return entity.level().environmentAttributes()
                    .getValue(net.minecraft.world.attribute.EnvironmentAttributes.WATER_EVAPORATES, entity.blockPosition())
                    ? 0.007D : 0.0023333333333333335D;
        }

        @Override
        public void setItemMovement(ItemEntity entity) {
            Vec3 vec3 = entity.getDeltaMovement();
            entity.setDeltaMovement(vec3.x * 0.95D, vec3.y < 0.06D ? vec3.y + 5.0E-4D : vec3.y, vec3.z * 0.95D);
        }

        @Override
        public boolean move(FluidState state, LivingEntity entity, Vec3 movementVector, double gravity) {
            return true;
        }
    }

    public abstract static class NewLavaFluid extends LavaFluid {

        @Override
        public Fluid getFlowing() {
            return FLOWING_NEW_LAVA.get();
        }

        @Override
        public Fluid getSource() {
            return NEW_LAVA.get();
        }

        @Override
        public Item getBucket() {
            return BHItems.NEW_LAVA_BUCKET.get();
        }

        @Override
        public net.neoforged.neoforge.fluids.FluidType getFluidType() {
            return NEW_LAVA_TYPE.get();
        }

        @Override
        public BlockState createLegacyBlock(FluidState fluidState) {
            return NEW_LAVA_BLOCK.get().defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(fluidState));
        }

        @Override
        public boolean isSame(Fluid other) {
            return other instanceof NewLavaFluid;
        }

        public static class Source extends NewLavaFluid {
            @Override
            public int getAmount(FluidState fluidState) {
                return 8;
            }

            @Override
            public boolean isSource(FluidState fluidState) {
                return true;
            }
        }

        public static class Flowing extends NewLavaFluid {
            @Override
            protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
                super.createFluidStateDefinition(builder);
                builder.add(LEVEL);
            }

            @Override
            public int getAmount(FluidState fluidState) {
                return fluidState.getValue(LEVEL);
            }

            @Override
            public boolean isSource(FluidState fluidState) {
                return false;
            }
        }
    }
}

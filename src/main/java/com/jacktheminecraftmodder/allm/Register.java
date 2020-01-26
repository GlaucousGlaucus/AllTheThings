package com.jacktheminecraftmodder.allm;

import com.jacktheminecraftmodder.allm.content.Blocks.BioGen;
import com.jacktheminecraftmodder.allm.content.Blocks.ElectricAlloyFurnace;
import com.jacktheminecraftmodder.allm.content.Blocks.MysticFurnace;
import com.jacktheminecraftmodder.allm.content.Enchantments.FlowerPower;
import com.jacktheminecraftmodder.allm.content.Tileentities.BioGenTileEntity;
import com.jacktheminecraftmodder.allm.content.Tileentities.ElectricAlloyFurnaceTile;
import com.jacktheminecraftmodder.allm.content.Tileentities.MysticFurnaceTile;
import com.jacktheminecraftmodder.allm.content.containers.BioGeneratorContainer;
import com.jacktheminecraftmodder.allm.content.containers.ElectricAlloyFurnaceContainer;
import com.jacktheminecraftmodder.allm.content.containers.MysticFurnaceContainer;
import com.jacktheminecraftmodder.allm.setup.ModSetup;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Register {
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, Reference.MOD_ID);
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = new DeferredRegister<>(ForgeRegistries.CONTAINERS, Reference.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, Reference.MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, Reference.MOD_ID);
    public static final DeferredRegister<Enchantment> ENCH = new DeferredRegister<>(ForgeRegistries.ENCHANTMENTS, Reference.MOD_ID);
   // public static final DeferredRegister<IRecipeSerializer<?>> RECIPIES = new DeferredRegister<>(ForgeRegistries.RECIPE_SERIALIZERS, Reference.MID);
   // public static final DeferredRegister<Potion> POTION = new DeferredRegister<>(ForgeRegistries.POTIONS, Reference.MID);
   // public static final DeferredRegister<Effect> POTION_TYPES = new DeferredRegister<>(ForgeRegistries.POTION_TYPES, Reference.MID);

    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINER_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENCH.register(FMLJavaModLoadingContext.get().getModEventBus());
     //   ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
      //  DIMENSIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    //Recipes

   // public static final RegistryObject<IRecipeSerializer<AbstractMysticSmeltingRecipe>> MYSTIC_SMELTING_RECIPE = RECIPIES.register("mystic_smelting", () -> new MysticSmeltingRecipeSerializer(MysticFurnaceRecipe::new, 100));

    //Enchantments

    public static final RegistryObject<Enchantment> FLOWER_POWER = ENCH.register("flower_power", () -> new FlowerPower(EquipmentSlotType.values()));

    //Blocks

    public static final RegistryObject<Block> BIO_GENERATOR = BLOCKS.register("bio_generator", () -> new BioGen());
    public static final RegistryObject<Block> ELECTRIC_ALLOY_FURNACE = BLOCKS.register("electric_alloy_furnace", () -> new ElectricAlloyFurnace(Block.Properties.create(Material.IRON).hardnessAndResistance(8.0f, 100.0f).lightValue(14)));
    public static final RegistryObject<Block> MYSTIC_FURNACE = BLOCKS.register("mystic_furnace", () -> new MysticFurnace());
    public static final RegistryObject<Block> TEST_BLOCK = BLOCKS.register("test_block", () -> new Block(Block.Properties.create(Material.ROCK)));

    //Containers

    public static final RegistryObject<ContainerType<BioGeneratorContainer>> BIO_GENERATOR_CONTAINER = CONTAINER_TYPES.register("bio_generator", () -> IForgeContainerType.create(BioGeneratorContainer::new));
    public static final RegistryObject<ContainerType<ElectricAlloyFurnaceContainer>> ELECTRIC_ALLOY_FURNACE_CONTAINER = CONTAINER_TYPES.register("electric_alloy_furnace", () -> IForgeContainerType.create(ElectricAlloyFurnaceContainer::new));
    public static final RegistryObject<ContainerType<MysticFurnaceContainer>> MYSTIC_FURNACE_CONTAINER = CONTAINER_TYPES.register("mystic_furnace", () -> IForgeContainerType.create(MysticFurnaceContainer::new));

    //Items

    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test_item", () -> new Item(new Item.Properties().group(ModSetup.ALL_THE_THINGS)));

    //BlockItems

    public static final RegistryObject<Item> BIO_GENERATOR_ITEM = ITEMS.register("bio_generator", () -> new BlockItem(BIO_GENERATOR.get() , new Item.Properties().group(ModSetup.ALL_THE_THINGS)));
    public static final RegistryObject<Item> ELECTRIC_ALLOY_FURNACE_ITEM = ITEMS.register("electric_alloy_furnace", () -> new BlockItem(ELECTRIC_ALLOY_FURNACE.get(), new Item.Properties().group(ModSetup.ALL_THE_THINGS)));
    public static final RegistryObject<Item> MYSTIC_FURNACE_ITEM = ITEMS.register("mystic_furnace", () -> new BlockItem(MYSTIC_FURNACE.get(), new Item.Properties().group(ModSetup.ALL_THE_THINGS)));
    public static final RegistryObject<Item> TEST_BLOCK_ITEM = ITEMS.register("test_block", () -> new BlockItem(TEST_BLOCK.get(), new Item.Properties().group(ModSetup.ALL_THE_THINGS)));

    //TE Types

    public static final RegistryObject<TileEntityType<BioGenTileEntity>> BIO_GEN_TILE = TILE_ENTITY_TYPES.register("bio_generator", () -> TileEntityType.Builder.create(BioGenTileEntity::new, BIO_GENERATOR.get()).build(null));
    public static final RegistryObject<TileEntityType<ElectricAlloyFurnaceTile>> ELECTRIC_ALLOY_FURNACE_TILE = TILE_ENTITY_TYPES.register("electric_alloy_furnace", () -> TileEntityType.Builder.create(ElectricAlloyFurnaceTile::new, ELECTRIC_ALLOY_FURNACE.get()).build(null));
    public static final RegistryObject<TileEntityType<MysticFurnaceTile>> MYSTIC_FURNACE_TILE = TILE_ENTITY_TYPES.register("mystic_furnace", () -> TileEntityType.Builder.create(MysticFurnaceTile::new, MYSTIC_FURNACE.get()).build(null));

}

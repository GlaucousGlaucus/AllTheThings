package com.jacktheminecraftmodder.allm;

import com.jacktheminecraftmodder.allm.base.ModifierItem;
import com.jacktheminecraftmodder.allm.content.Blocks.BioGen;
import com.jacktheminecraftmodder.allm.content.Blocks.ElectricAlloyFurnace;
import com.jacktheminecraftmodder.allm.content.Blocks.MysticFurnace;
import com.jacktheminecraftmodder.allm.content.Enchantments.DeathWalkerEnchantment;
import com.jacktheminecraftmodder.allm.content.Enchantments.FlowerPower;
import com.jacktheminecraftmodder.allm.content.Potions.ParalysedEffect;
import com.jacktheminecraftmodder.allm.content.Tileentities.BioGenTileEntity;
import com.jacktheminecraftmodder.allm.content.Tileentities.ElectricAlloyFurnaceTile;
import com.jacktheminecraftmodder.allm.content.Tileentities.MysticFurnaceTile;
import com.jacktheminecraftmodder.allm.content.containers.BioGeneratorContainer;
import com.jacktheminecraftmodder.allm.content.containers.ElectricAlloyFurnaceContainer;
import com.jacktheminecraftmodder.allm.content.containers.MysticFurnaceContainer;
import com.jacktheminecraftmodder.allm.fluids.MysticWater;
import com.jacktheminecraftmodder.allm.recipes.MysticFurnace.MysticSmeltingRecipeSerializer;
import com.jacktheminecraftmodder.allm.recipes.MysticFurnace.NewMysticSmeltingRecipe;
import com.jacktheminecraftmodder.allm.setup.ModSetup;
import com.jacktheminecraftmodder.allm.world.biomes.EnchantedEarth;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.biome.Biome;
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
    public static final DeferredRegister<Fluid> FLUIDS = new DeferredRegister<>(ForgeRegistries.FLUIDS, Reference.MOD_ID);
 //   public static final DeferredRegister<Feature<?>> FEATURE = new DeferredRegister<>(ForgeRegistries.FEATURES, Reference.MOD_ID);
    public static final DeferredRegister<Potion> POTION = new DeferredRegister<>(ForgeRegistries.POTION_TYPES, Reference.MOD_ID);
    public static final DeferredRegister<Effect> EFFECTS = new DeferredRegister<>(ForgeRegistries.POTIONS, Reference.MOD_ID);
    public static final DeferredRegister<Biome> BIOMES = new DeferredRegister<>(ForgeRegistries.BIOMES, Reference.MOD_ID);
    public static final DeferredRegister<IRecipeSerializer<?>> RERIPES = new DeferredRegister<>(ForgeRegistries.RECIPE_SERIALIZERS, Reference.MOD_ID);
  //  public static final DeferredRegister<IRecipeType<?>> RERIPES = new DeferredRegister<>(ForgeRegistries.POI_TYPES, Reference.MOD_ID);

    public static void init() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        TILE_ENTITY_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        CONTAINER_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ENCH.register(FMLJavaModLoadingContext.get().getModEventBus());
        POTION.register(FMLJavaModLoadingContext.get().getModEventBus());
        EFFECTS.register(FMLJavaModLoadingContext.get().getModEventBus());
        FLUIDS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BIOMES.register(FMLJavaModLoadingContext.get().getModEventBus());
      //  FEATURE.register(FMLJavaModLoadingContext.get().getModEventBus());
     //   ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
      //  DIMENSIONS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    //Biomes

    public static final RegistryObject<Biome> ENCHANTED_EARTH_BIOME = BIOMES.register("enchanted_earth_biome", () -> new EnchantedEarth());

    //Fluids

    public static final RegistryObject<FlowingFluid> MYSTIC_WATER = FLUIDS.register("mystic_water", () -> new MysticWater.Source());

    public static final RegistryObject<FlowingFluid> MYSTIC_WATER_FLOWING = FLUIDS.register("mystic_water_flowing", () -> new MysticWater.Flowing());

    //Features

  //  public static final RegistryObject<Feature<MysticalTreeFeature>> MYSTICAL_TREE_FEATURE = FEATURE.register("mystical_tree_feature", () -> new MysticalTreeFeature());

    //Enchantments

    public static final RegistryObject<Enchantment> FLOWER_POWER = ENCH.register("flower_power", () -> new FlowerPower(EquipmentSlotType.values()));
    public static final RegistryObject<Enchantment> DEATH_WALKER = ENCH.register("death_walker", () -> new DeathWalkerEnchantment(Enchantment.Rarity.VERY_RARE, EquipmentSlotType.FEET));

    //Blocks

    public static final RegistryObject<Block> BIO_GENERATOR = BLOCKS.register("bio_generator", () -> new BioGen());
    public static final RegistryObject<Block> ELECTRIC_ALLOY_FURNACE = BLOCKS.register("electric_alloy_furnace", () -> new ElectricAlloyFurnace(Block.Properties.create(Material.IRON).hardnessAndResistance(8.0f, 100.0f).lightValue(14)));
    public static final RegistryObject<Block> MYSTIC_FURNACE = BLOCKS.register("mystic_furnace", () -> new MysticFurnace());
    public static final RegistryObject<Block> TEST_BLOCK = BLOCKS.register("test_block", () -> new Block(Block.Properties.create(Material.ROCK)));
    public static final RegistryObject<FlowingFluidBlock> MYSTIC_WATER_BLOCK = BLOCKS.register("mystic_water", () -> new FlowingFluidBlock(() ->  MYSTIC_WATER.get(), Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops()));
    public static final RegistryObject<Block> DRIED_LUCK_ORE = BLOCKS.register("dried_luck_ore", () -> new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(10.0F, 100.0F)));
 //   public static final RegistryObject<Block> MYSTIC_CRAFTING_TABLE = BLOCKS.register("mystic_crafting_table", () -> new MysticCraftingTable());
  //  public static final RegistryObject<Block> MYSTIC_LEAVES = BLOCKS.register("mystic_leaves", () -> new LeavesBlock(Block.Properties.create(Material.LEAVES)));
   // public static final RegistryObject<Block> MYSTIC_LOG = BLOCKS.register("mystic_log", () -> new LogBlock(Block.Properties.create(Material.WOOD)));
   // public static final RegistryObject<Block> MYSTIC_TREE_SAPLING = BLOCKS.register("mystic_tree_sapling", () -> new ModSaplingBlock(MysticalTree.class, Block.Properties.create(Material.ROCK)));

    //Containers

    public static final RegistryObject<ContainerType<BioGeneratorContainer>> BIO_GENERATOR_CONTAINER = CONTAINER_TYPES.register("bio_generator", () -> IForgeContainerType.create(BioGeneratorContainer::new));
    public static final RegistryObject<ContainerType<ElectricAlloyFurnaceContainer>> ELECTRIC_ALLOY_FURNACE_CONTAINER = CONTAINER_TYPES.register("electric_alloy_furnace", () -> IForgeContainerType.create(ElectricAlloyFurnaceContainer::new));
    public static final RegistryObject<ContainerType<MysticFurnaceContainer>> MYSTIC_FURNACE_CONTAINER = CONTAINER_TYPES.register("mystic_furnace", () -> IForgeContainerType.create(MysticFurnaceContainer::new));
  //  public static final RegistryObject<ContainerType<MysticCraftingTableContainer>> MYSTIC_CRAFTING_TABLE_CONTAINER = CONTAINER_TYPES.register("mystic_crafting_table", () -> IForgeContainerType.create(MysticCraftingTableContainer::new));

    //Items

    public static final RegistryObject<Item> TEST_ITEM = ITEMS.register("test_item", () -> new Item(new Item.Properties().group(ModSetup.ALL_THE_THINGS)));
    public static final RegistryObject<Item> MYSTIC_WATER_BUCKET = ITEMS.register("mystic_water_bucket", () -> new BucketItem(() ->  MYSTIC_WATER.get(), new Item.Properties().group(ModSetup.ALL_THE_THINGS)));

    //Modifiers

    public static final RegistryObject<Item> TEST_MODIFIER = ITEMS.register("test_modifier", () -> new ModifierItem(200,"Hello", TextFormatting.AQUA));

    //BlockItems

    public static final RegistryObject<Item> BIO_GENERATOR_ITEM = ITEMS.register("bio_generator", () -> new BlockItem(BIO_GENERATOR.get() , new Item.Properties().group(ModSetup.ALL_THE_THINGS)));
    public static final RegistryObject<Item> ELECTRIC_ALLOY_FURNACE_ITEM = ITEMS.register("electric_alloy_furnace", () -> new BlockItem(ELECTRIC_ALLOY_FURNACE.get(), new Item.Properties().group(ModSetup.ALL_THE_THINGS)));
    public static final RegistryObject<Item> MYSTIC_FURNACE_ITEM = ITEMS.register("mystic_furnace", () -> new BlockItem(MYSTIC_FURNACE.get(), new Item.Properties().group(ModSetup.ALL_THE_THINGS)));
    public static final RegistryObject<Item> TEST_BLOCK_ITEM = ITEMS.register("test_block", () -> new BlockItem(TEST_BLOCK.get(), new Item.Properties().group(ModSetup.ALL_THE_THINGS)));
    public static final RegistryObject<Item> DRIED_LUCK_ORE_ITEM = ITEMS.register("dried_luck_ore", () -> new BlockItem(DRIED_LUCK_ORE.get(), new Item.Properties().group(ModSetup.ALL_THE_THINGS)));

    //TE Types

    public static final RegistryObject<TileEntityType<BioGenTileEntity>> BIO_GEN_TILE = TILE_ENTITY_TYPES.register("bio_generator", () -> TileEntityType.Builder.create(BioGenTileEntity::new, BIO_GENERATOR.get()).build(null));
    public static final RegistryObject<TileEntityType<ElectricAlloyFurnaceTile>> ELECTRIC_ALLOY_FURNACE_TILE = TILE_ENTITY_TYPES.register("electric_alloy_furnace", () -> TileEntityType.Builder.create(ElectricAlloyFurnaceTile::new, ELECTRIC_ALLOY_FURNACE.get()).build(null));
    public static final RegistryObject<TileEntityType<MysticFurnaceTile>> MYSTIC_FURNACE_TILE = TILE_ENTITY_TYPES.register("mystic_furnace", () -> TileEntityType.Builder.create(MysticFurnaceTile::new, MYSTIC_FURNACE.get()).build(null));

    //Effects

    public static final RegistryObject<Effect> PARALYSED_EFFECT = EFFECTS.register("paralysed", () -> new ParalysedEffect(EffectType.HARMFUL, 5535058));

    //Potions

    public static final RegistryObject<Potion> PARALYSED_POTION = POTION.register("paralysed", () -> new Potion(new EffectInstance(PARALYSED_EFFECT.get(), 9000)));

    public static final RegistryObject<MysticSmeltingRecipeSerializer<NewMysticSmeltingRecipe>> MYSTIC_SMELTING_SERIALIZER = RERIPES.register("mystic_smelting", () -> new MysticSmeltingRecipeSerializer<>(50));
}

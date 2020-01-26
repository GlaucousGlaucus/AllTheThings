package com.jacktheminecraftmodder.allm.datagen;

import com.jacktheminecraftmodder.allm.Register;
import com.jacktheminecraftmodder.allm.base.BaseLootTableProvider;
import net.minecraft.data.DataGenerator;

public class LootTables extends BaseLootTableProvider {

    public LootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
        lootTable.put(Register.TEST_BLOCK.get(), createStandardTable("test_block", Register.TEST_BLOCK.get()));
    }

}

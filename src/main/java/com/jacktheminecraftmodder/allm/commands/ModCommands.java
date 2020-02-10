package com.jacktheminecraftmodder.allm.commands;

import com.jacktheminecraftmodder.allm.Reference;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class ModCommands {

    public static void register(CommandDispatcher<CommandSource> dispatcher) {
        LiteralCommandNode<CommandSource> cmdTut = dispatcher.register(
                Commands.literal(Reference.MOD_ID)
                      //  .then(CommandTest.register(dispatcher))
                       // .then(CommandTpDim.register(dispatcher))
                       // .then(CommandSpawner.register(dispatcher))
        );

        dispatcher.register(Commands.literal("allm").redirect(cmdTut));
    }

}

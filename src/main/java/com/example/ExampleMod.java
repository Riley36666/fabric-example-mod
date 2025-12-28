package com.example;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExampleMod implements ModInitializer {
	public static final String MOD_ID = "modid";


	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        com.example.ModItems.init();
        com.example.ModItemGroups.init();
        Commands.registercommands();
        LOGGER.info("Client loaded successfully!");
	}
}
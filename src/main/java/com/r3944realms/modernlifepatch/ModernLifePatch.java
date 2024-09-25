package com.r3944realms.modernlifepatch;

import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.r3944realms.modernlifepatch.ModernLifePatch.MOD_ID;

@Mod(value = MOD_ID)
public class ModernLifePatch {
    public static final String MOD_ID = "modernlifepatch";
    public static final Logger logger = LoggerFactory.getLogger(ModernLifePatch.class);
    ModernLifePatch() {
        logger.info("ModernLifePatch loaded");
    }
}

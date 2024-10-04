package com.r3944realms.modernlifepatch.datagen.provider;

import com.r3944realms.modernlifepatch.datagen.lang.ModLangKeyValue;
import com.r3944realms.modernlifepatch.utils.Enum.LanguageEnum;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModLanguageProvider extends LanguageProvider {
    private final LanguageEnum Language;
    private final Map<String, String> LanKeyMap;
    private static final List<String> objects = new ArrayList<>();
    public ModLanguageProvider(DataGenerator dataGenerator, String modId, LanguageEnum Lan) {
        super(dataGenerator, modId, Lan.local);
        this.Language = Lan;
        LanKeyMap = new HashMap<>();
        init();
    }
    private void init() {
        for (ModLangKeyValue key : ModLangKeyValue.values()) {
            addLang(key.getKey(), ModLangKeyValue.getLan(Language, key));
        }
    }
    private void addLang(String Key, String value) {
        if(!objects.contains(Key)) objects.add(Key);
        LanKeyMap.put(Key, value);
    }

    @Override
    protected void addTranslations() {
        objects.forEach(key -> add(key,LanKeyMap.get(key)));
    }
}

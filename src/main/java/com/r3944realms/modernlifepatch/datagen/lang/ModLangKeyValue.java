package com.r3944realms.modernlifepatch.datagen.lang;

import com.r3944realms.modernlifepatch.ModernLifePatch;
import com.r3944realms.modernlifepatch.utils.Enum.LanguageEnum;
import com.r3944realms.modernlifepatch.utils.String.StringValidator;

public enum ModLangKeyValue {
    MOD_RESOURCE_NAME(getCustomTextKey("resource_pack", ModernLifePatch.MOD_ID, "name"), "§b§lModern Life §fExclusive Materials", "§b§l摩登生活 §f专属材质"),
    MOD_RESOURCE_DESC(getCustomTextKey("resource_pack", ModernLifePatch.MOD_ID ,"description"), "All of textures and models are created by BF_Meow_，Do not use for other purposes without permission.", "所有材质和模型均由 白帆小喵BF_Meow_ 绘制。未经允许，请勿用作他用。"),

    POWER_RECEIVER_HOVER_FIRST(getHoverTextKey("power_receiver", "1"), "Receives power on the selected channel from all transmitters", "从所有能量发射器中去接收所选工作频道的能量"),
    POWER_RECEIVER_AND_TRANSMITTER_HOVER_SECOND(getHoverTextKey("power_receiver", "2"), "Right click block to change current channel", "右键方块打开界面，来切换工作频道"),


    TURNTABLE_HOVER(getHoverTextKey("turn_table"), "Rotates clockwise when powered by redstone", "当有红石充能时顺时针旋转"),
    POWER_TRANSMITTER_FIRST(getHoverTextKey("power_transmitter", "1"), "Transmits power on the selected channel to all receivers", "将红石信号传输给对应频道的所有能量接收器"),
    TRASH_CAN_HOVER(getHoverTextKey("trash_can"), "Items inserted into the trash slot will be deleted when the trash can is full", "当垃圾桶已满时，进入垃圾桶的物品将被删除"),

    PHOTOCOPIER_HOVER_FIRST(getHoverTextKey("photocopier", "1"), "Interact with a canvas or photograph to make copies", "可以用于照片或画作的复制"),
    PHOTOCOPIER_HOVER_SECOND(getHoverTextKey("photocopier", "2"), " * Requires paper", " * 需要纸"),

    EXTRACTOR_HOVER(getHoverTextKey("extractor"), "Extracts items from attached chests or from the space in front of it", "从其吸取端处的箱子或空间中取出物品"),
    CHESS_BOARD_HOVER(getHoverTextKey("chess_board"), "PLace it down and begin a new game!", "放置后使用即可开始一场新游戏!"),
    WALL_SHELF_HOVER_FIRST(getHoverTextKey("wall_shelf", "1"), "Right click tp place an item on the shelf", "右键以放置物品在墙架上"),
    WALL_SHELF_HOVER_SECOND(getHoverTextKey("wall_shelf", "2"), "Shift-right-click with an empty hand to remove an item", "空手按住Shift键+右键即可取下物品"),
    STEAM_RADIATOR_HOVER(getHoverTextKey("steam_radiator"), "Right click placed radiator to activate", "右键已放置的散热器，即可激活"),
    //P
    NEED_RED_STONE_POWER_HOVER(getHoverTextKey("need_rs_power"), "Requires redstone power to operate", "需要红石充能"),
    STORE_18_HOVER(getHoverTextKey("store_18"), "Stores up to 18 items", "可存储18组物品"),
    //~P
    METAL_GRATE_HOVER(getHoverTextKey("metal_grate"), "Allows water and items to flow through freely", "允许水与物品自由地穿过"),
    EASEL_HOVER(getHoverTextKey("easel"), "Place a canvas on it to begin painting", "放置(帆布)画布在其上，即可开始绘画"),
    //CONTAINER & MENU
    KITCHEN_CABINET_MENU_LABEL(getMenuLabelKey("kitchen_cabinet"), "Kitchen Cabinet", "厨房柜子"),
    KITCHEN_DRAWER_CABINET_MENU_LABEL(getMenuLabelKey("kitchen_drawer_cabinet"), "Kitchen Drawer Cabinet", "厨房抽屉柜"),
    TRASH_CAN_MENU_LABEL(getMenuLabelKey("trash_can"), "Trash Can", "垃圾桶"),
    PHOTOCOPIER_MENU_LABEL(getMenuLabelKey("photo_copier"), "Photocopier", "复印机"),
    PRINTER_MENU_LABEL(getMenuLabelKey("printer"), "Printer", "打印机"),
    PRINTER_SIZE_MENU_LABEL(getMenuLabelKey("print_size"), "Print Size", "打印尺寸"),
    NIGHT_STAND_MENU_LABEL(getMenuLabelKey("night_stand"), "Night Stand", "床头柜"),
    STEAM_RADIATOR_MENU_LABEL(getMenuLabelKey("radiator"), "Radiator" ,"蒸汽散热器"),
    //CANVAS
    CANVAS_HOVER_DESC_COMPLETED(getHoverTextKey("canvas", "desc_completed"), "A work of art" ,"艺术品"),
    CANVAS_HOVER_UNIQUE_ID(getHoverTextKey("canvas", "unique_id"), "ID = %d", "ID = %d"),
    CANVAS_HOVER_PIXELS_SIZE(getHoverTextKey("canvas", "pixels_size"), "%d x %d pixels", "%d x %d 像素"),
    CANVAS_HOVER_BLOCK_SIZE(getHoverTextKey("canvas", "block_size"), "%f x %f ", "%f x %f"),
    CANVAS_HOVER_OFFSET(getHoverTextKey("canvas", "offset"), "(%f,%f)", "(%f,%f)"),
    CANVAS_HOVER_DESC_EMPTY(getHoverTextKey("canvas", "desc_empty"), "For use with an easel", "可用画架来创作"),
    CANVAS_HOVER_DESC_SIZE_NORMAL(getHoverTextKey("canvas", "normal_size"), "Normal print", "常规尺寸画作"),
    CANVAS_HOVER_DESC_SIZE_LARGE(getHoverTextKey("canvas", "large_size"), "Large print", "大型尺寸画作"),
    CANVAS_HOVER_DESC_SIZE_EXTRA_LARGE(getHoverTextKey("canvas", "extra_large_size"), "Extra Large print", "超大型尺寸画作"),
    CANVAS_HOVER_DESC_SIZE_MASSIVE(getHoverTextKey("canvas", "massive_size"), "Massive print", "巨大尺寸的画作"),
    CANVAS_HOVER_DESC_SIZE_LARGE_SQUARE(getHoverTextKey("canvas", "large_square_size"),"Large square print", "大型方寸画作"),
    CANVAS_HOVER_DESC_SIZE_EXTRA_LARGE_SQUARE(getHoverTextKey("canvas", "extra_large_square_size"), "Extra Large square", "超大型方寸画作"),
    CANVAS_HOVER_DESC_SIZE_MASSIVE_SQUARE(getHoverTextKey("canvas", "massive_square_size"),"Massive square print","巨大方寸画作"),
    CANVAS_HOVER_DESC_SIZE_CUSTOM(getHoverTextKey("canvas", "custom_size"), "Custom print", "自定义尺寸画作"),
    ;

    private final String key;
    private final String US_EN;
    private final String SIM_CN;
    ModLangKeyValue(String key, String US_EN, String SIM_CN) {
        this.key = key;
        this.US_EN = US_EN;
        this.SIM_CN = SIM_CN;
    }
    public String getKey() {
        return key;
    }
    public static String getLan(LanguageEnum lan, ModLangKeyValue key) {
        if (lan == LanguageEnum.SimpleChinese) {
            return getSimpleChinese(key);
        }
        return getEnglish(key);
    }

    private static String getEnglish(ModLangKeyValue key) {
        return key.US_EN;
    }
    private static String getSimpleChinese(ModLangKeyValue key) {
        return key.SIM_CN;
    }
    private static final String HEAD_ = "modernlifepatch.lang.";
    public static String getHoverTextKey(String key) {
        return getHoverTextKey(key, "0");
    }
    public static String getCustomTextKey(String prefix, String mod_id, String custom) {
        if(StringValidator.isNotValidMinecraftKey(prefix) && StringValidator.isNotValidMinecraftKey(mod_id) && StringValidator.isNotValidMinecraftKey(custom)) {
            throw new IllegalArgumentException("Not valid MinecraftKey which only including [a-z] and '_'.");
        }
        return HEAD_ + prefix + "." + mod_id + "." + custom;
    }
    public static String getHoverTextKey(String itemName, String diffMark) {
        if(StringValidator.isNotValidMinecraftKey(itemName) || StringValidator.isNotValidMinecraftKey(diffMark)) {
            throw new IllegalArgumentException("Not valid MinecraftKey which only including [a-z] and [0-9] and '_'.");
        }
        return HEAD_ + itemName + ".hover_text." + diffMark;
    }
    public static String getMenuLabelKey(String menuLabel) {
        if(StringValidator.isNotValidMinecraftKey(menuLabel)) {
            throw new IllegalArgumentException("Not valid MinecraftKey which only including [a-z] and '_'.");
        }
        return HEAD_ + "menu." + menuLabel;
    }
}

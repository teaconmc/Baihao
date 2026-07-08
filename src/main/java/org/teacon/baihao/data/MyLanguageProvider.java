package org.teacon.baihao.data;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.teacon.baihao.Baihao;
import org.teacon.baihao.common.BHItems;

public abstract class MyLanguageProvider extends LanguageProvider {
    public MyLanguageProvider(PackOutput output, String locale) {
        super(output, Baihao.MODID, locale);
    }

    public static class English extends MyLanguageProvider{

        public English(PackOutput output) {
            super(output, "en_us");
        }

        @Override
        protected void addTranslations() {
            this.addItem(BHItems.VERY_HARMFUL_AXE, "very harmful axe");
            this.addItem(BHItems.HARMFUL_SWORD, "harmful sword");
            this.addItem(BHItems.VITALITY_HELMET, "vitality netherite helmet");
            this.addItem(BHItems.VITALITY_CHESTPLATE, "vitality netherite chestplate");
            this.addItem(BHItems.VITALITY_LEGGINGS, "vitality netherite leggings");
            this.addItem(BHItems.VITALITY_BOOTS, "vitality netherite boots");
        }
    }

    public static class Chinese extends MyLanguageProvider {

        public Chinese(PackOutput output) {
            super(output, "zh_cn");
        }

        @Override
        protected void addTranslations() {
            this.addItem(BHItems.VERY_HARMFUL_AXE, "很痛的斧");
            this.addItem(BHItems.HARMFUL_SWORD, "有点痛的剑");
            this.addItem(BHItems.VITALITY_HELMET, "活力下界合金头盔");
            this.addItem(BHItems.VITALITY_CHESTPLATE, "活力下界合金胸甲");
            this.addItem(BHItems.VITALITY_LEGGINGS, "活力下界合金护腿");
            this.addItem(BHItems.VITALITY_BOOTS, "活力下界合金靴子");
        }
    }
}

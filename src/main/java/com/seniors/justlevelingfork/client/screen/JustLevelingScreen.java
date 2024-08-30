package com.seniors.justlevelingfork.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.seniors.justlevelingfork.client.core.SortPassives;
import com.seniors.justlevelingfork.client.core.SortSkills;
import com.seniors.justlevelingfork.client.core.Utils;
import com.seniors.justlevelingfork.client.gui.DrawTabs;
import com.seniors.justlevelingfork.common.capability.AptitudeCapability;
import com.seniors.justlevelingfork.handler.HandlerCommonConfig;
import com.seniors.justlevelingfork.handler.HandlerConfigClient;
import com.seniors.justlevelingfork.handler.HandlerResources;
import com.seniors.justlevelingfork.network.packet.common.*;
import com.seniors.justlevelingfork.registry.RegistryAptitudes;
import com.seniors.justlevelingfork.registry.RegistryTitles;
import com.seniors.justlevelingfork.registry.aptitude.Aptitude;
import com.seniors.justlevelingfork.registry.passive.Passive;
import com.seniors.justlevelingfork.registry.skills.Skill;
import com.seniors.justlevelingfork.registry.title.Title;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@OnlyIn(Dist.CLIENT)
public class JustLevelingScreen extends Screen {
    public static Minecraft client = Minecraft.getInstance();
    public int selectedPage = 0;
    public String selectedAptitude = "";

    public boolean checkMouse = false;

    public boolean isMouseCheck = false;
    public boolean b = true;
    public int maxTick = 40;
    public int tick = 0;

    public int skillSize = 0;
    public int skillActualPage = 0;
    public int skillSizePage = 0;

    public int scrollYOff = 0;
    public int scrollDropDown = 0;

    public boolean scrollingDropDown = false;
    public String searchValue = "";
    private EditBox searchTitle;

    public JustLevelingScreen() {
        super(new TranslatableComponent("screen.aptitude.title"));
    }


    protected void init() {
        int x = (this.width - 176) / 2;
        int y = (this.height - 166) / 2;

        this.scrollYOff = y + 33;

        this.searchTitle = new EditBox(this.font, x + 88 - 47, y + 17, 93, 12, new TranslatableComponent("screen.title.search"));
        this.searchTitle.setMaxLength(50);
        this.searchTitle.setBordered(true);
        this.searchTitle.setTextColor(16777215);
        this.searchTitle.setFocus(true);
        this.searchTitle.setValue(this.searchValue);

        super.init();
    }


    public void render(@NotNull PoseStack matrixStack, int mouseX, int mouseY, float delta) {
        this.isMouseCheck = false;
        int x = (this.width - 176) / 2;
        int y = (this.height - 166) / 2;

        drawBackground(matrixStack, x, y, mouseX, mouseY, delta);

        super.render(matrixStack, mouseX, mouseY, delta);
    }

    public void drawBackground(PoseStack matrixStack, int x, int y, int mouseX, int mouseY, float delta) {
        renderBackground(matrixStack);
        assert client.player != null;
        int progress = (int) (client.player.experienceProgress * 151.0F);
        //matrixStack.pushPose();
        if (this.selectedPage == 0) {
            RenderSystem.enableBlend();
            RenderSystem.setShaderTexture(0, HandlerResources.SKILL_PAGE[0]);
            this.blit(matrixStack, x, y, 0, 0, 176, 166);
            this.blit(matrixStack, x + 12, y + 43, 0, 166, progress, 5);
        }
        if (this.selectedPage == 1) {
            RenderSystem.enableBlend();
            if (RegistryAptitudes.getAptitude(this.selectedAptitude).background != null) {
                RenderSystem.setShaderTexture(0, RegistryAptitudes.getAptitude(this.selectedAptitude).background);
                blit(matrixStack, x + 7, y + 30, 0.0F, 0.0F, 160, 128, 16, 16);
            }

            RenderSystem.setShaderTexture(0, HandlerResources.SKILL_PAGE[1]);
            this.blit(matrixStack, x, y, 0, 0, 176, 166);
        }
        if (this.selectedPage == 2) {
            RenderSystem.enableBlend();
            RenderSystem.setShaderTexture(0, HandlerResources.SKILL_PAGE[2]);
            this.blit(matrixStack, x, y, 0, 0, 176, 166);
        }

        DrawTabs.render(this, matrixStack, mouseX, mouseY, 176, 166, 0);
        if (this.selectedPage == 0) {
            this.drawAptitudes(matrixStack, x, y, mouseX, mouseY);
        }

        if (this.selectedPage == 1) {
            this.drawSkills(matrixStack, x, y, mouseX, mouseY);
        }

        if (this.selectedPage == 2) {
            this.drawTitles(matrixStack, x, y, mouseX, mouseY, delta);
        }

        //matrixStack.popPose();
    }

    public void drawTitles(PoseStack matrixStack, int x, int y, int mouseX, int mouseY, float delta) {
        Utils.drawCenter(matrixStack, new TranslatableComponent("screen.title.choose_your_title"), x + 88, y + 7);

        this.searchTitle.setVisible(true);
        this.searchTitle.render(matrixStack, mouseX, mouseY, delta);

        List<Title> titleList = RegistryTitles.TITLES_REGISTRY.get().getValues().stream().toList();
        List<Title> unlockTitles = new ArrayList<>();
        List<Title> lockTitles = new ArrayList<>();
        for (Title title : titleList) {
            if (title.getRequirement()) {
                unlockTitles.add(title);
                continue;
            }
            if (!title.HideRequirements)
                lockTitles.add(title);
        }

        unlockTitles.sort(new SortTitleByName());
        lockTitles.sort(new SortTitleByName());
        List<Title> sorted = new ArrayList<>();
        sorted.addAll(unlockTitles);
        sorted.addAll(lockTitles);
        List<Title> searchTitleList = this.searchTitle.getValue().isEmpty() ? sorted : new ArrayList<>();
        if (!this.searchTitle.getValue().isEmpty() && !this.searchTitle.getValue().equals(" ")) {
            for (Title title : sorted) {
                if (new TranslatableComponent(title.getKey()).getString().toLowerCase().contains(this.searchTitle.getValue().toLowerCase())) {
                    searchTitleList.add(title);
                }
            }
        }

        int maxSize = 9;
        int size = Math.min(searchTitleList.size(), maxSize);
        if (this.scrollDropDown > (searchTitleList.size() - maxSize))
            this.scrollDropDown = (searchTitleList.size() - maxSize);
        if (this.scrollDropDown < 0) this.scrollDropDown = 0;

        matrixStack.pushPose();

        int scrollX;
        int scrollY;
        for (scrollX = this.scrollDropDown; scrollX < this.scrollDropDown + size; ++scrollX) {
            Title title = searchTitleList.get(scrollX);
            boolean checkTitle = title == RegistryTitles.getTitle(AptitudeCapability.get().getPlayerTitle());
            scrollY = title.getRequirement() ? (checkTitle ? Color.GREEN.getRGB() : Color.WHITE.getRGB()) : Color.DARK_GRAY.getRGB();
            client.font.draw(matrixStack, new TranslatableComponent(title.getKey()), x + 10, y + 34 + 12 * (scrollX - this.scrollDropDown), scrollY);
            if (Utils.checkMouse(x + 8, y + 33 + 12 * (scrollX - this.scrollDropDown), mouseX, mouseY, 142, 10) && !this.scrollingDropDown) {
                RenderSystem.enableBlend();
                RenderSystem.setShaderTexture(0, HandlerResources.SKILL_PAGE[this.selectedPage]);
                this.blit(matrixStack, x + 8, y + 33 + 12 * (scrollX - this.scrollDropDown), 0, 166, 142, 10);
                this.isMouseCheck = true;
                if (this.checkMouse) {
                    if (title.getRequirement()) {
                        SetPlayerTitleSP.send(title);
                        Utils.playSound();
                    }

                    this.checkMouse = false;
                }
            }
        }

        if (Utils.checkMouse(x + 156, y + 33, mouseX, mouseY, 12, 106)) {
            this.isMouseCheck = true;
            if (this.checkMouse) {
                this.scrollingDropDown = true;
                Utils.playSound();
                this.checkMouse = false;
            }
        }

        if (this.scrollingDropDown) {
            this.scrollYOff = mouseY - 8;
            this.scrollYOff = Mth.clamp(this.scrollYOff, y + 33, y + 33 + 106 - 15);
            this.scrollDropDown = Math.round((float) (searchTitleList.size() - maxSize) / 91.0F * (float) (this.scrollYOff - (y + 33)));
        }

        scrollX = x + 156;
        double scrollYF = (91.0F / (float) (searchTitleList.size() - maxSize) * (float) this.scrollDropDown);
        scrollY = (int) ((double) (y + 33) + scrollYF);
        RenderSystem.setShaderTexture(0, HandlerResources.SKILL_PAGE[this.selectedPage]);
        this.blit(matrixStack, scrollX, scrollY, 176, 0, 12, 15);
        RenderSystem.setShaderTexture(0, HandlerResources.SKILL_PAGE[1]);
        this.blit(matrixStack, x + 16, y + 144, 30, 167, 11, 11);
        if (Utils.checkMouse(x + 16, y + 144, mouseX, mouseY, 11, 11) && !this.scrollingDropDown) {
            RenderSystem.setShaderTexture(0, HandlerResources.SKILL_PAGE[1]);
            this.blit(matrixStack, x + 16, y + 144, 30, 179, 11, 11);
            List<Component> tooltipList = new ArrayList<>();
            tooltipList.add(new TranslatableComponent("tooltip.sort.button.mod_names").withStyle(ChatFormatting.DARK_AQUA));
            tooltipList.add(new TranslatableComponent("tooltip.sort.button.true").withStyle((Boolean) HandlerConfigClient.showTitleModName.get() ? ChatFormatting.GREEN : ChatFormatting.DARK_GRAY));
            tooltipList.add(new TranslatableComponent("tooltip.sort.button.false").withStyle(!(Boolean) HandlerConfigClient.showTitleModName.get() ? ChatFormatting.GREEN : ChatFormatting.DARK_GRAY));
            assert client.screen != null;
            Utils.drawToolTipList(this, matrixStack, tooltipList, mouseX, mouseY);
            this.isMouseCheck = true;
            if (this.checkMouse) {
                HandlerConfigClient.showTitleModName.set(!(Boolean) HandlerConfigClient.showTitleModName.get());
                Utils.playSound();
                this.checkMouse = false;
            }
        }

        int backIconX = x + 141;
        int backIconY = y + 144;
        RenderSystem.setShaderTexture(0, HandlerResources.SKILL_PAGE[this.selectedPage]);
        this.blit(matrixStack, backIconX, backIconY, 204, 0, 18, 10);
        if (Utils.checkMouse(backIconX, backIconY, mouseX, mouseY, 18, 10) && !this.scrollingDropDown) {
            RenderSystem.setShaderTexture(0, HandlerResources.SKILL_PAGE[this.selectedPage]);
            this.blit(matrixStack, backIconX, backIconY, 222, 0, 18, 10);
            Utils.drawToolTip(this, matrixStack, new TranslatableComponent("tooltip.title.back"), mouseX, mouseY);
            this.isMouseCheck = true;
            if (this.checkMouse) {
                this.skillActualPage = 0;
                this.selectedPage = 0;
                Utils.playSound();
                this.checkMouse = false;
            }
        }

        matrixStack.popPose();
    }

    public void drawAptitudes(PoseStack matrixStack, int x, int y, int mouseX, int mouseY) {
        assert client.player != null;

        Utils.drawCenter(matrixStack, client.player.getName(), x + 88, y + 7);
        Utils.drawCenter(matrixStack, new TranslatableComponent("screen.aptitude.level", client.player.experienceLevel, client.player.totalExperience), x + 88, y + 17);
        Title titleKey = RegistryTitles.getTitle(AptitudeCapability.get().getPlayerTitle());
        String title = (titleKey != null) ? new TranslatableComponent(RegistryTitles.getTitle(AptitudeCapability.get().getPlayerTitle()).getKey()).getString() : "";
        int titleWidth = client.font.width(title) + 15;
        boolean checkButton = Utils.checkMouse(x + 88 - titleWidth / 2 - 2, y + 27, mouseX, mouseY, titleWidth + 2, 14);

        RenderSystem.setShaderTexture(0, HandlerResources.SKILL_PAGE[this.selectedPage]);
        this.blit(matrixStack, x + 88 - titleWidth / 2 - 2, y + 27, checkButton ? 4 : 0, 214, 2, 14);
        this.blit(matrixStack, x + 88 - titleWidth / 2, y + 27, 0, checkButton ? 228 : 242, titleWidth, 14);
        this.blit(matrixStack, x + 88 + titleWidth / 2, y + 27, checkButton ? 6 : 2, 214, 2, 14);

        client.font.drawShadow(matrixStack, title, x + 88 - titleWidth / 2 + 2, y + 30, Color.WHITE.getRGB());
        RenderSystem.setShaderTexture(0, HandlerResources.SKILL_PAGE[this.selectedPage]);
        this.blit(matrixStack, x + 88 + titleWidth / 2 - 10, y + 30, 8, 218, 8, 8);

        if (checkButton) {
            Utils.drawToolTip(this, matrixStack, new TextComponent("Edit Title"), mouseX, mouseY);
            this.isMouseCheck = true;
            if (this.checkMouse) {
                this.selectedPage = 2;
                Utils.playSound();
                this.checkMouse = false;
            }
        }

        List<Aptitude> aptitudeList = new ArrayList<>(RegistryAptitudes.APTITUDES_REGISTRY.get().getValues().stream().toList());
        aptitudeList.sort(new SortAptitudeByDateCreated());
        for (int i = 0; i < aptitudeList.size(); i++) {
            Aptitude aptitude = aptitudeList.get(i);
            String key = aptitude.getKey();
            int aptitudeLevel = aptitude.getLevel();

            int xPos = x + 12 + i % 2 * 77;
            int yPos = y + 50 + (i - i % 2) / 2 * 28;

            if (Utils.checkMouse(xPos, yPos, mouseX, mouseY, 74, 26)){
                RenderSystem.setShaderTexture(0, HandlerResources.SKILL_PAGE[this.selectedPage]);
                this.blit(matrixStack, xPos, yPos, 176, 0, 73, 26);
            }


            RenderSystem.setShaderTexture(0, aptitude.getLockedTexture());
            this.blit(matrixStack, xPos + 5, yPos + 5, 0.0F, 0.0F, 16, 16, 16, 16);

            client.font.draw(matrixStack, new TranslatableComponent(key + ".abbreviation").withStyle(ChatFormatting.BOLD), xPos + 24, yPos + 5, (new Color(240, 240, 240)).getRGB());

            client.font.draw(matrixStack, new TranslatableComponent("screen.aptitude.experience", Utils.numberFormat(aptitudeLevel), HandlerCommonConfig.HANDLER.instance().aptitudeMaxLevel), xPos + 24, yPos + 14, (new Color(170, 170, 170)).getRGB());

            if (Utils.checkMouse(xPos, yPos, mouseX, mouseY, 74, 26)) {
                Utils.drawToolTip(this, matrixStack, new TranslatableComponent(key), mouseX, mouseY);
                this.isMouseCheck = true;
                if (this.checkMouse) {
                    this.tick = this.maxTick / 2;
                    this.b = true;
                    this.selectedAptitude = aptitude.getName();
                    this.selectedPage = 1;
                    Utils.playSound();
                    this.checkMouse = false;
                }
            }
        }
    }

    public void drawSkills(PoseStack matrixStack, int x, int y, int mouseX, int mouseY) {
        assert client.player != null;
        assert client.screen != null;
        AptitudeCapability capability = AptitudeCapability.get();
        Aptitude aptitude = RegistryAptitudes.getAptitude(this.selectedAptitude);
        String key = aptitude.getKey();
        int aptitudeLevel = aptitude.getLevel();

        String rank = aptitude.getRank(aptitudeLevel).getString();

        RenderSystem.setShaderTexture(0, aptitude.getLockedTexture());
        this.blit(matrixStack, x + 12, y + 9, 0.0F, 0.0F, 16, 16, 16, 16);

        client.font.draw(matrixStack, new TranslatableComponent(key).withStyle(ChatFormatting.BOLD), x + 34, y + 8, Utils.FONT_COLOR);
        client.font.draw(matrixStack, new TranslatableComponent("screen.skill.level_and_rank", Utils.numberFormat(aptitudeLevel), HandlerCommonConfig.HANDLER.instance().aptitudeMaxLevel, rank), x + 34, y + 18, Utils.FONT_COLOR);

        RenderSystem.setShaderTexture(0, HandlerResources.SKILL_PAGE[this.selectedPage]);
        blit(matrixStack, x + 16, y + 144, 30, 167, 11, 11);
        if (Utils.checkMouse(x + 16, y + 144, mouseX, mouseY, 11, 11)) {
            RenderSystem.setShaderTexture(0, HandlerResources.SKILL_PAGE[this.selectedPage]);
            blit(matrixStack, x + 16, y + 144, 30, 179, 11, 11);
            List<Component> tooltipList = new ArrayList<>();
            tooltipList.add(new TranslatableComponent("tooltip.sort.button.mod_names").withStyle(ChatFormatting.DARK_AQUA));
            tooltipList.add(new TranslatableComponent("tooltip.sort.button.true").withStyle(HandlerConfigClient.showSkillModName.get() ? ChatFormatting.GREEN : ChatFormatting.DARK_GRAY));
            tooltipList.add(new TranslatableComponent("tooltip.sort.button.false").withStyle(!HandlerConfigClient.showSkillModName.get() ? ChatFormatting.GREEN : ChatFormatting.DARK_GRAY));
            Utils.drawToolTipList(this, matrixStack, tooltipList, mouseX, mouseY);
            this.isMouseCheck = true;
            if (this.checkMouse) {
                HandlerConfigClient.showSkillModName.set(!HandlerConfigClient.showSkillModName.get());
                Utils.playSound();
                this.checkMouse = false;
            }
        }

        RenderSystem.setShaderTexture(0, HandlerResources.SKILL_PAGE[this.selectedPage]);
        blit(matrixStack, x + 28, y + 144, 42, 167, 11, 11);
        if (Utils.checkMouse(x + 28, y + 144, mouseX, mouseY, 11, 11)) {
            List<Component> tooltipList = new ArrayList<>();
            tooltipList.add(new TranslatableComponent("tooltip.sort.button.passives").withStyle(ChatFormatting.DARK_AQUA));
            for (int m = 0; m < (SortPassives.values()).length; m++) {
                ChatFormatting color = (SortPassives.values()[m] == HandlerConfigClient.sortPassive.get()) ? ChatFormatting.GREEN : ChatFormatting.DARK_GRAY;
                tooltipList.add(new TranslatableComponent((SortPassives.values()[m]).order).withStyle(color));
            }
            Utils.drawToolTipList(this, matrixStack, tooltipList, mouseX, mouseY);
            this.isMouseCheck = true;
            if (this.checkMouse) {
                HandlerConfigClient.sortPassive.set(SortPassives.fromIndex(HandlerConfigClient.sortPassive.get().index + 1));
                Utils.playSound();
                this.checkMouse = false;
            }
        }

        RenderSystem.setShaderTexture(0, HandlerResources.SKILL_PAGE[this.selectedPage]);
        this.blit(matrixStack, x + 40, y + 144, 54, 167, 11, 11);
        if (Utils.checkMouse(x + 40, y + 144, mouseX, mouseY, 11, 11)) {
            RenderSystem.setShaderTexture(0, HandlerResources.SKILL_PAGE[this.selectedPage]);
            this.blit(matrixStack, x + 40, y + 144, 54, 179, 11, 11);
            List<Component> tooltipList = new ArrayList<>();
            tooltipList.add(new TranslatableComponent("tooltip.sort.button.skills").withStyle(ChatFormatting.DARK_AQUA));
            for (int m = 0; m < (SortSkills.values()).length; m++) {
                ChatFormatting color = (SortSkills.values()[m] == HandlerConfigClient.sortSkill.get()) ? ChatFormatting.GREEN : ChatFormatting.DARK_GRAY;
                tooltipList.add(new TranslatableComponent((SortSkills.values()[m]).order).withStyle(color));
            }
            Utils.drawToolTipList(this, matrixStack, tooltipList, mouseX, mouseY);
            this.isMouseCheck = true;
            if (this.checkMouse) {
                HandlerConfigClient.sortSkill.set(SortSkills.fromIndex(HandlerConfigClient.sortSkill.get().index + 1));
                Utils.playSound();
                this.checkMouse = false;
            }
        }

        List<Passive> listPassives = new ArrayList<>(aptitude.getPassives(aptitude));
        List<Skill> listSkills = new ArrayList<>(aptitude.getSkills(aptitude));

        switch (HandlerConfigClient.sortPassive.get()) {
            case ByName:
                listPassives.sort(new SortPassiveByName());
                break;
            case ByReverseName:
                listPassives.sort((new SortPassiveByName()).reversed());
                break;
        }

        switch (HandlerConfigClient.sortSkill.get()) {
            case ByName:
                listSkills.sort(new SortSkillByName());
                break;
            case ByReverseName:
                listSkills.sort((new SortSkillByName()).reversed());
                break;
            case ByLevel:
                listSkills.sort(new SortSkillList());
                break;
        }

        List<Object> sorted = new ArrayList<>();
        sorted.addAll(listPassives);
        sorted.addAll(listSkills);
        int i;
        for (i = 0; i < sorted.size(); ) {
            this.skillSize = (i - i % 5) / 5;
            i++;
        }
        for (i = 0; i <= this.skillSize; ) {
            this.skillSizePage = (i - i % 4) / 4;
            i++;
        }

        List<List<List<Object>>> listPage = new ArrayList<>();
        List<List<Object>> listSorted = new ArrayList<>();
        int k;
        for (k = 0; k < sorted.size(); k++) {
            if ((k - k % 5) / 5 == this.skillSize) {
                listSorted.add((k - k % 5) / 5, sorted.subList((k - k % 5) / 5 * 5, sorted.size()));
            } else {
                listSorted.add((k - k % 5) / 5, sorted.subList((k - k % 5) / 5 * 5, 5 + (k - k % 5) / 5 * 5));
            }
        }

        for (k = 0; k < this.skillSize + 1; k++) {
            if ((k - k % 4) / 4 == this.skillSizePage) {
                listPage.add((k - k % 4) / 4, listSorted.subList((k - k % 4) / 4 * 4, this.skillSize + 1));
            } else {
                listPage.add((k - k % 4) / 4, listSorted.subList((k - k % 4) / 4 * 4, 4 + (k - k % 4) / 4 * 4));
            }
        }

        List<List<Object>> newPage = listPage.get(this.skillActualPage);
        AtomicInteger in = new AtomicInteger(-1);
        newPage.forEach(list -> {
            in.addAndGet(1);

            createList(list, capability, matrixStack, x, y + in.get() * 26 - newPage.size() * 13, mouseX, mouseY);
        });
        if (this.tick >= this.maxTick) {
            this.b = !this.b;
            this.tick = 0;
        }
        int j = (aptitudeLevel < HandlerCommonConfig.HANDLER.instance().aptitudeMaxLevel) ? (this.b ? 6 : 0) : 12;

        RenderSystem.setShaderTexture(0, HandlerResources.SKILL_PAGE[this.selectedPage]);
        this.blit(matrixStack, x + 153, y + 14, 177 + j, 1, 6, 6);

        boolean canLevelUpAptitude = (client.player.isCreative()
                || AptitudeLevelUpSP.requiredPoints(aptitudeLevel) <= client.player.totalExperience);
        if (Utils.checkMouse(x + 149, y + 10, mouseX, mouseY, 14, 14)) {
            if (AptitudeCapability.get(client.player).getGlobalLevel() >= HandlerCommonConfig.HANDLER.instance().playersMaxGlobalLevel) {
                Utils.drawToolTip(this, matrixStack,
                        new TranslatableComponent("tooltip.aptitude.global_max_level", HandlerCommonConfig.HANDLER.instance().playersMaxGlobalLevel)
                                .withStyle(ChatFormatting.RED),
                        mouseX,
                        mouseY);
            } else if (aptitudeLevel < HandlerCommonConfig.HANDLER.instance().aptitudeMaxLevel) {
                ChatFormatting color = canLevelUpAptitude ? ChatFormatting.GREEN : ChatFormatting.RED;
                Utils.drawToolTip(this, matrixStack, new TranslatableComponent("tooltip.aptitude.level_up", new TextComponent(String.valueOf(AptitudeLevelUpSP.requiredLevels(aptitudeLevel))).withStyle(color),
                        new TextComponent(String.valueOf(AptitudeLevelUpSP.requiredPoints(aptitudeLevel))).withStyle(color),
                        new TranslatableComponent(aptitude.getKey()).withStyle(color)).withStyle(ChatFormatting.GRAY), mouseX, mouseY);
                this.tick = this.maxTick - 5;
                if (canLevelUpAptitude) {
                    this.b = true;
                    this.isMouseCheck = true;
                    if (this.checkMouse) {
                        Utils.playSound();
                        AptitudeLevelUpSP.send(aptitude);
                        this.checkMouse = false;
                    }
                } else {
                    this.b = false;
                }
            } else {
                Utils.drawToolTip(this, matrixStack, new TranslatableComponent("tooltip.aptitude.max_level", new TranslatableComponent(aptitude.getKey()).withStyle(ChatFormatting.GREEN)).withStyle(ChatFormatting.GRAY), mouseX, mouseY);
            }

        } else if (canLevelUpAptitude) {
            this.tick++;
        } else {
            this.b = false;
        }


        int backIconX = x + 141;
        int backIconY = y + 144;

        RenderSystem.setShaderTexture(0, HandlerResources.SKILL_PAGE[this.selectedPage]);
        blit(matrixStack, backIconX, backIconY, 204, 0, 18, 10);
        if (Utils.checkMouse(backIconX, backIconY, mouseX, mouseY, 18, 10)) {
            RenderSystem.setShaderTexture(0, HandlerResources.SKILL_PAGE[this.selectedPage]);
            blit(matrixStack, backIconX, backIconY, 222, 0, 18, 10);
            Utils.drawToolTip(this, matrixStack, new TranslatableComponent("tooltip.skill.back"), mouseX, mouseY);
            this.isMouseCheck = true;
            if (this.checkMouse) {
                this.skillActualPage = 0;
                this.selectedPage = 0;
                Utils.playSound();
                this.checkMouse = false;
            }
        }

        if (this.skillSizePage > 0) {
            String pageNumber = "" + this.skillActualPage + 1 + "/" + this.skillActualPage + 1;
            int pageIconX = x + 88 - client.font.width(pageNumber) / 2;
            int pageIconY = y + 144;

            client.font.draw(matrixStack, pageNumber, pageIconX, pageIconY + 2, Color.WHITE.getRGB());

            if (this.skillActualPage > 0) {
                boolean select = Utils.checkMouse(pageIconX - 12, pageIconY, mouseX, mouseY, 7, 11);
                RenderSystem.setShaderTexture(0, HandlerResources.SKILL_PAGE[this.selectedPage]);
                blit(matrixStack, pageIconX - 12, pageIconY, 241, select ? 12 : 0, 7, 11);
                if (select) {
                    Utils.drawToolTip(this, matrixStack, new TranslatableComponent("tooltip.skill.previous"), mouseX, mouseY);
                    this.isMouseCheck = true;
                    if (this.checkMouse) {
                        --this.skillActualPage;
                        Utils.playSound();
                        this.checkMouse = false;
                    }
                }
            }

            if (this.skillActualPage < this.skillSizePage) {
                boolean select = Utils.checkMouse(pageIconX + client.font.width(pageNumber) + 5, pageIconY, mouseX, mouseY, 7, 11);
                RenderSystem.setShaderTexture(0, HandlerResources.SKILL_PAGE[this.selectedPage]);
                blit(matrixStack, pageIconX + client.font.width(pageNumber) + 5, pageIconY, 249, select ? 12 : 0, 7, 11);
                if (select) {
                    Utils.drawToolTip(this, matrixStack, new TranslatableComponent("tooltip.skill.next"), mouseX, mouseY);
                    this.isMouseCheck = true;
                    if (this.checkMouse) {
                        ++this.skillActualPage;
                        Utils.playSound();
                        this.checkMouse = false;
                    }
                }
            }
        }
    }

    public void createList(List<Object> list, AptitudeCapability capability, PoseStack matrixStack, int x, int y, int mouseX, int mouseY) {
        assert client.screen != null;
        for (int i = 0; i < list.size(); i++) {

            int xTexture = x - 10 + 88 + 26 * i - 13 * (list.size() - 1);
            int yTexture = y - 10 + 90 + 13;

            int xIcon = x - 12 + 88 + 26 * i - 13 * (list.size() - 1);
            int yIcon = y - 12 + 90 + 13;

            Object object = list.get(i);
            if (object instanceof Passive passive) {
                int isMax = (passive.getLevel() == passive.getMaxLevel()) ? 24 : 0;
                RenderSystem.setShaderTexture(0, passive.getTexture());
                this.blit(matrixStack, xTexture, yTexture, 0.0F, 0.0F, 20, 20, 20, 20);
                RenderSystem.setShaderTexture(0, HandlerResources.SKILL_ICONS);
                this.blit(matrixStack, xIcon, yIcon, 0.0F, isMax, 24, 24, 72, 72);
                int centerTextureX = xIcon + 9 - client.font.width(String.valueOf(passive.getLevel())) / 2;

                int iconAdd = (passive.getLevel() < passive.getMaxLevel() && AptitudeCapability.get().getAptitudeLevel(passive.aptitude) >= passive.getNextLevelUp()) ? 10 : 0;
                int iconLess = (passive.getLevel() > 0) ? 10 : 0;

                if (Utils.checkMouse(xIcon, yIcon, mouseX, mouseY, 24, 24)) {
                    if (Utils.checkMouse(xIcon + 2, yIcon + 2, mouseX, mouseY, 9, 9) &&
                            passive.getLevel() > 0) {
                        iconLess = 20;
                        this.isMouseCheck = true;
                        if (this.checkMouse) {
                            Utils.playSound();
                            PassiveLevelDownSP.send(passive);
                            this.checkMouse = false;
                        }
                    }

                    if (Utils.checkMouse(xIcon + 13, yIcon + 2, mouseX, mouseY, 9, 9) &&
                            passive.getLevel() < passive.getMaxLevel() && AptitudeCapability.get().getAptitudeLevel(passive.aptitude) >= passive.getNextLevelUp()) {
                        iconAdd = 20;
                        this.isMouseCheck = true;
                        if (this.checkMouse) {
                            Utils.playSound();
                            PassiveLevelUpSP.send(passive);
                            this.checkMouse = false;
                        }
                    }

                    //matrixStack.pushPose();
                    Utils.drawToolTipList(this, matrixStack, passive.tooltip(), mouseX, mouseY);
                    RenderSystem.enableBlend();
                    RenderSystem.setShaderTexture(0, HandlerResources.SKILL_ICONS);
                    this.blit(matrixStack, xIcon, yIcon, 0.0F, 48.0F, 24, 24, 72, 72);
                    RenderSystem.setShaderTexture(0, HandlerResources.SKILL_PAGE[this.selectedPage]);
                    this.blit(matrixStack, xIcon + 2, yIcon + 2, 1, 167 + iconLess, 9, 9);
                    this.blit(matrixStack, xIcon + 13, yIcon + 2, 11, 167 + iconAdd, 9, 9);
                    //matrixStack.popPose();
                }

                RenderSystem.setShaderTexture(0, HandlerResources.SKILL_PAGE[this.selectedPage]);
                blit(matrixStack, centerTextureX, yIcon + 17, 21, 167, 7, 8);
                client.font.draw(matrixStack, String.valueOf(passive.getLevel()), centerTextureX + 8, yIcon + 18, Color.BLACK.getRGB());
                client.font.draw(matrixStack, String.valueOf(passive.getLevel()), centerTextureX + 7, yIcon + 17, Color.WHITE.getRGB());

            }


            object = list.get(i);
            if (object instanceof Skill skill) {
                int isToggle = 0;
                if (skill.canSkill()) isToggle = 24;
                RenderSystem.setShaderTexture(0, skill.getTexture());
                this.blit(matrixStack, xTexture, yTexture, 0.0F, 0.0F, 20, 20, 20, 20);
                RenderSystem.setShaderTexture(0, HandlerResources.SKILL_ICONS);
                this.blit(matrixStack, xIcon, yIcon, 24.0F, isToggle, 24, 24, 72, 72);
                if (!skill.getToggle()) {
                    matrixStack.pushPose();
                    RenderSystem.enableBlend();
                    RenderSystem.setShaderTexture(0, HandlerResources.SKILL_ICONS);
                    this.blit(matrixStack, xIcon, yIcon, 24.0F, 48.0F, 24, 24, 72, 72);
                    matrixStack.popPose();
                }
                if (Utils.checkMouse(xIcon, yIcon, mouseX, mouseY, 24, 24)) {
                    Utils.drawToolTipList(this, matrixStack, skill.tooltip(), mouseX, mouseY);
                    if (skill.getToggle()) {
                        this.isMouseCheck = true;
                        if (this.checkMouse) {
                            Utils.playSound();
                            ToggleSkillSP.send(skill, !capability.getToggleSkill(skill));
                            this.checkMouse = false;
                        }
                    }
                }
            }

        }
    }

    public boolean isPauseScreen() {
        return false;
    }


    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (this.scrollingDropDown) this.scrollingDropDown = false;
        return super.mouseReleased(mouseX, mouseY, button);
    }


    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        if (this.selectedPage == 2) {
            this.scrollDropDown = (int) (this.scrollDropDown - amount);
            return true;
        }

        return super.mouseScrolled(mouseX, mouseY, amount);
    }


    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0 && this.isMouseCheck) this.checkMouse = true;
        DrawTabs.mouseClicked(button);
        return super.mouseClicked(mouseX, mouseY, button);
    }


    public boolean charTyped(char chr, int modifiers) {
        if (this.selectedPage == 2) {
            boolean b = this.searchTitle.charTyped(chr, modifiers);
            this.searchValue = this.searchTitle.getValue();
            return b;
        }
        return super.charTyped(chr, modifiers);
    }


    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.selectedPage == 2) {
            this.searchTitle.keyPressed(keyCode, scanCode, modifiers);
            boolean b = (keyCode != 256 || super.keyPressed(keyCode, scanCode, modifiers));
            this.searchValue = this.searchTitle.getValue();
            return b;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }


    public void onClose() {
        this.checkMouse = false;
        this.skillActualPage = 0;
        this.selectedPage = 0;
        this.searchValue = "";
        this.searchTitle.setValue("");
        DrawTabs.onClose();
        super.onClose();
    }

    public static class SortAptitudeByDateCreated
            implements Comparator<Aptitude> {
        public int compare(Aptitude date1, Aptitude date2) {
            return date1.index - date2.index;
        }
    }

    public static class SortPassiveByName
            implements Comparator<Passive> {
        public int compare(Passive name1, Passive name2) {
            return name1.getName().compareTo(name2.getName());
        }
    }

    public static class SortSkillByName
            implements Comparator<Skill> {
        public int compare(Skill name1, Skill name2) {
            return name1.getName().compareTo(name2.getName());
        }
    }

    public static class SortSkillList
            implements Comparator<Skill> {
        public int compare(Skill lvl1, Skill lvl2) {
            return lvl1.requiredLevel - lvl2.requiredLevel;
        }
    }

    public static class SortTitleByName
            implements Comparator<Title> {
        public int compare(Title name1, Title name2) {
            return name1.getName().compareTo(name2.getName());
        }
    }
}



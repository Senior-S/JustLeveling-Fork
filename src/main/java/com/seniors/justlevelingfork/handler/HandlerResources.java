 package com.seniors.justlevelingfork.handler;
 
 import net.minecraft.resources.ResourceLocation;
 
 public class HandlerResources
 {
   public static final ResourceLocation[] SKILL_PAGE = new ResourceLocation[] {
       create("textures/gui/container/skill_page_1.png"), 
       create("textures/gui/container/skill_page_2.png"), 
       create("textures/gui/container/skill_page_3.png")
     };
   
   public static final ResourceLocation[] STRENGTH_LOCKED_ICON = new ResourceLocation[] {
       create("textures/skill/strength/locked_0.png"), 
       create("textures/skill/strength/locked_8.png"), 
       create("textures/skill/strength/locked_16.png"), 
       create("textures/skill/strength/locked_24.png")
     };
   
   public static final ResourceLocation[] CONSTITUTION_LOCKED_ICON = new ResourceLocation[] {
       create("textures/skill/constitution/locked_0.png"), 
       create("textures/skill/constitution/locked_8.png"), 
       create("textures/skill/constitution/locked_16.png"), 
       create("textures/skill/constitution/locked_24.png")
     };
   
   public static final ResourceLocation[] DEXTERITY_LOCKED_ICON = new ResourceLocation[] {
       create("textures/skill/dexterity/locked_0.png"), 
       create("textures/skill/dexterity/locked_8.png"), 
       create("textures/skill/dexterity/locked_16.png"), 
       create("textures/skill/dexterity/locked_24.png")
     };
   
   public static final ResourceLocation[] DEFENSE_LOCKED_ICON = new ResourceLocation[] {
       create("textures/skill/defense/locked_0.png"), 
       create("textures/skill/defense/locked_8.png"), 
       create("textures/skill/defense/locked_16.png"), 
       create("textures/skill/defense/locked_24.png")
     };
   
   public static final ResourceLocation[] INTELLIGENCE_LOCKED_ICON = new ResourceLocation[] {
       create("textures/skill/intelligence/locked_0.png"), 
       create("textures/skill/intelligence/locked_8.png"), 
       create("textures/skill/intelligence/locked_16.png"), 
       create("textures/skill/intelligence/locked_24.png")
     };
   
   public static final ResourceLocation[] BUILDING_LOCKED_ICON = new ResourceLocation[] {
       create("textures/skill/building/locked_0.png"), 
       create("textures/skill/building/locked_8.png"), 
       create("textures/skill/building/locked_16.png"), 
       create("textures/skill/building/locked_24.png")
     };
   
   public static final ResourceLocation[] MAGIC_LOCKED_ICON = new ResourceLocation[] {
       create("textures/skill/magic/locked_0.png"), 
       create("textures/skill/magic/locked_8.png"), 
       create("textures/skill/magic/locked_16.png"), 
       create("textures/skill/magic/locked_24.png")
     };
   
   public static final ResourceLocation[] LUCK_LOCKED_ICON = new ResourceLocation[] {
       create("textures/skill/luck/locked_0.png"), 
       create("textures/skill/luck/locked_8.png"), 
       create("textures/skill/luck/locked_16.png"), 
       create("textures/skill/luck/locked_24.png")
     };
   
   public static final ResourceLocation SKILL_ICONS = create("textures/skill/icons.png");
   public static final ResourceLocation NULL_SKILL = create("textures/skill/null_skill.png");
   
   public static final ResourceLocation ONE_HANDED_SKILL = create("textures/skill/strength/one_handed.png");
   public static final ResourceLocation FIGHTING_SPIRIT_SKILL = create("textures/skill/strength/fighting_spirit.png");
   public static final ResourceLocation BERSERKER_SKILL = create("textures/skill/strength/berserker.png");
   
   public static final ResourceLocation ATHLETICS_SKILL = create("textures/skill/constitution/athletics.png");
   public static final ResourceLocation TURTLE_SHIELD_SKILL = create("textures/skill/constitution/turtle_shield.png");
   public static final ResourceLocation LION_HEART_SKILL = create("textures/skill/constitution/lion_heart.png");
   
   public static final ResourceLocation QUICK_REPOSITION_SKILL = create("textures/skill/dexterity/quick_reposition.png");
   public static final ResourceLocation STEALTH_MASTERY_SKILL = create("textures/skill/dexterity/stealth_mastery.png");
   public static final ResourceLocation CAT_EYES_SKILL = create("textures/skill/dexterity/cat_eyes.png");
   
   public static final ResourceLocation SNOW_WALKER_SKILL = create("textures/skill/defense/snow_walker.png");
   public static final ResourceLocation COUNTER_ATTACK_SKILL = create("textures/skill/defense/counter_attack.png");
   public static final ResourceLocation DIAMOND_SKIN_SKILL = create("textures/skill/defense/diamond_skin.png");
   
   public static final ResourceLocation SCHOLAR_SKILL = create("textures/skill/intelligence/scholar.png");
   public static final ResourceLocation HAGGLER_SKILL = create("textures/skill/intelligence/haggler.png");
   public static final ResourceLocation ALCHEMY_MANIPULATION_SKILL = create("textures/skill/intelligence/alchemy_manipulation.png");
   
   public static final ResourceLocation OBSIDIAN_SMASHER_SKILL = create("textures/skill/building/obsidian_smasher.png");
   public static final ResourceLocation TREASURE_HUNTER_SKILL = create("textures/skill/building/treasure_hunter.png");
   public static final ResourceLocation CONVERGENCE_SKILL = create("textures/skill/building/convergence.png");
   
   public static final ResourceLocation SAFE_PORT_SKILL = create("textures/skill/magic/safe_port.png");
   public static final ResourceLocation LIFE_EATER_SKILL = create("textures/skill/magic/life_eater.png");
   public static final ResourceLocation WORMHOLE_STORAGE_SKILL = create("textures/skill/magic/wormhole_storage.png");
   
   public static final ResourceLocation CRITICAL_ROLL_SKILL = create("textures/skill/luck/critical_roll.png");
   public static final ResourceLocation LUCKY_DROP_SKILL = create("textures/skill/luck/lucky_drop.png");
   public static final ResourceLocation LIMIT_BREAKER_SKILL = create("textures/skill/luck/limit_breaker.png");
   
   public static ResourceLocation create(String path) {
     return new ResourceLocation("justlevelingfork", path);
   }
 }



package net.minecraft.data;

import java.util.Optional;
import java.util.stream.IntStream;
import net.minecraft.util.ResourceLocation;

public class StockModelShapes {
   public static final ModelsUtil field_240262_a_ = func_240333_a_("cube", StockTextureAliases.field_240406_c_, StockTextureAliases.field_240413_j_, StockTextureAliases.field_240414_k_, StockTextureAliases.field_240415_l_, StockTextureAliases.field_240416_m_, StockTextureAliases.field_240417_n_, StockTextureAliases.field_240418_o_);
   public static final ModelsUtil field_240306_b_ = func_240333_a_("cube_directional", StockTextureAliases.field_240406_c_, StockTextureAliases.field_240413_j_, StockTextureAliases.field_240414_k_, StockTextureAliases.field_240415_l_, StockTextureAliases.field_240416_m_, StockTextureAliases.field_240417_n_, StockTextureAliases.field_240418_o_);
   public static final ModelsUtil field_240307_c_ = func_240333_a_("cube_all", StockTextureAliases.field_240404_a_);
   public static final ModelsUtil field_240308_d_ = func_240332_a_("cube_mirrored_all", "_mirrored", StockTextureAliases.field_240404_a_);
   public static final ModelsUtil field_240309_e_ = func_240333_a_("cube_column", StockTextureAliases.field_240407_d_, StockTextureAliases.field_240412_i_);
   public static final ModelsUtil field_240310_f_ = func_240332_a_("cube_column_horizontal", "_horizontal", StockTextureAliases.field_240407_d_, StockTextureAliases.field_240412_i_);
   public static final ModelsUtil field_240311_g_ = func_240333_a_("cube_top", StockTextureAliases.field_240409_f_, StockTextureAliases.field_240412_i_);
   public static final ModelsUtil field_240312_h_ = func_240333_a_("cube_bottom_top", StockTextureAliases.field_240409_f_, StockTextureAliases.field_240408_e_, StockTextureAliases.field_240412_i_);
   public static final ModelsUtil field_240313_i_ = func_240333_a_("orientable", StockTextureAliases.field_240409_f_, StockTextureAliases.field_240410_g_, StockTextureAliases.field_240412_i_);
   public static final ModelsUtil field_240314_j_ = func_240333_a_("orientable_with_bottom", StockTextureAliases.field_240409_f_, StockTextureAliases.field_240408_e_, StockTextureAliases.field_240412_i_, StockTextureAliases.field_240410_g_);
   public static final ModelsUtil field_240315_k_ = func_240332_a_("orientable_vertical", "_vertical", StockTextureAliases.field_240410_g_, StockTextureAliases.field_240412_i_);
   public static final ModelsUtil field_240316_l_ = func_240333_a_("button", StockTextureAliases.field_240405_b_);
   public static final ModelsUtil field_240317_m_ = func_240332_a_("button_pressed", "_pressed", StockTextureAliases.field_240405_b_);
   public static final ModelsUtil field_240318_n_ = func_240332_a_("button_inventory", "_inventory", StockTextureAliases.field_240405_b_);
   public static final ModelsUtil field_240319_o_ = func_240332_a_("door_bottom", "_bottom", StockTextureAliases.field_240409_f_, StockTextureAliases.field_240408_e_);
   public static final ModelsUtil field_240320_p_ = func_240332_a_("door_bottom_rh", "_bottom_hinge", StockTextureAliases.field_240409_f_, StockTextureAliases.field_240408_e_);
   public static final ModelsUtil field_240321_q_ = func_240332_a_("door_top", "_top", StockTextureAliases.field_240409_f_, StockTextureAliases.field_240408_e_);
   public static final ModelsUtil field_240322_r_ = func_240332_a_("door_top_rh", "_top_hinge", StockTextureAliases.field_240409_f_, StockTextureAliases.field_240408_e_);
   public static final ModelsUtil field_240323_s_ = func_240332_a_("fence_post", "_post", StockTextureAliases.field_240405_b_);
   public static final ModelsUtil field_240324_t_ = func_240332_a_("fence_side", "_side", StockTextureAliases.field_240405_b_);
   public static final ModelsUtil field_240325_u_ = func_240332_a_("fence_inventory", "_inventory", StockTextureAliases.field_240405_b_);
   public static final ModelsUtil field_240326_v_ = func_240332_a_("template_wall_post", "_post", StockTextureAliases.field_240421_r_);
   public static final ModelsUtil field_240327_w_ = func_240332_a_("template_wall_side", "_side", StockTextureAliases.field_240421_r_);
   public static final ModelsUtil field_240328_x_ = func_240332_a_("template_wall_side_tall", "_side_tall", StockTextureAliases.field_240421_r_);
   public static final ModelsUtil field_240329_y_ = func_240332_a_("wall_inventory", "_inventory", StockTextureAliases.field_240421_r_);
   public static final ModelsUtil field_240330_z_ = func_240333_a_("template_fence_gate", StockTextureAliases.field_240405_b_);
   public static final ModelsUtil field_240236_A_ = func_240332_a_("template_fence_gate_open", "_open", StockTextureAliases.field_240405_b_);
   public static final ModelsUtil field_240237_B_ = func_240332_a_("template_fence_gate_wall", "_wall", StockTextureAliases.field_240405_b_);
   public static final ModelsUtil field_240238_C_ = func_240332_a_("template_fence_gate_wall_open", "_wall_open", StockTextureAliases.field_240405_b_);
   public static final ModelsUtil field_240239_D_ = func_240333_a_("pressure_plate_up", StockTextureAliases.field_240405_b_);
   public static final ModelsUtil field_240240_E_ = func_240332_a_("pressure_plate_down", "_down", StockTextureAliases.field_240405_b_);
   public static final ModelsUtil field_240241_F_ = func_240334_a_(StockTextureAliases.field_240406_c_);
   public static final ModelsUtil field_240242_G_ = func_240333_a_("slab", StockTextureAliases.field_240408_e_, StockTextureAliases.field_240409_f_, StockTextureAliases.field_240412_i_);
   public static final ModelsUtil field_240243_H_ = func_240332_a_("slab_top", "_top", StockTextureAliases.field_240408_e_, StockTextureAliases.field_240409_f_, StockTextureAliases.field_240412_i_);
   public static final ModelsUtil field_240244_I_ = func_240333_a_("leaves", StockTextureAliases.field_240404_a_);
   public static final ModelsUtil field_240245_J_ = func_240333_a_("stairs", StockTextureAliases.field_240408_e_, StockTextureAliases.field_240409_f_, StockTextureAliases.field_240412_i_);
   public static final ModelsUtil field_240246_K_ = func_240332_a_("inner_stairs", "_inner", StockTextureAliases.field_240408_e_, StockTextureAliases.field_240409_f_, StockTextureAliases.field_240412_i_);
   public static final ModelsUtil field_240247_L_ = func_240332_a_("outer_stairs", "_outer", StockTextureAliases.field_240408_e_, StockTextureAliases.field_240409_f_, StockTextureAliases.field_240412_i_);
   public static final ModelsUtil field_240248_M_ = func_240332_a_("template_trapdoor_top", "_top", StockTextureAliases.field_240405_b_);
   public static final ModelsUtil field_240249_N_ = func_240332_a_("template_trapdoor_bottom", "_bottom", StockTextureAliases.field_240405_b_);
   public static final ModelsUtil field_240250_O_ = func_240332_a_("template_trapdoor_open", "_open", StockTextureAliases.field_240405_b_);
   public static final ModelsUtil field_240251_P_ = func_240332_a_("template_orientable_trapdoor_top", "_top", StockTextureAliases.field_240405_b_);
   public static final ModelsUtil field_240252_Q_ = func_240332_a_("template_orientable_trapdoor_bottom", "_bottom", StockTextureAliases.field_240405_b_);
   public static final ModelsUtil field_240253_R_ = func_240332_a_("template_orientable_trapdoor_open", "_open", StockTextureAliases.field_240405_b_);
   public static final ModelsUtil field_240254_S_ = func_240333_a_("cross", StockTextureAliases.field_240419_p_);
   public static final ModelsUtil field_240255_T_ = func_240333_a_("tinted_cross", StockTextureAliases.field_240419_p_);
   public static final ModelsUtil field_240256_U_ = func_240333_a_("flower_pot_cross", StockTextureAliases.field_240420_q_);
   public static final ModelsUtil field_240257_V_ = func_240333_a_("tinted_flower_pot_cross", StockTextureAliases.field_240420_q_);
   public static final ModelsUtil field_240258_W_ = func_240333_a_("rail_flat", StockTextureAliases.field_240422_s_);
   public static final ModelsUtil field_240259_X_ = func_240332_a_("rail_curved", "_corner", StockTextureAliases.field_240422_s_);
   public static final ModelsUtil field_240260_Y_ = func_240332_a_("template_rail_raised_ne", "_raised_ne", StockTextureAliases.field_240422_s_);
   public static final ModelsUtil field_240261_Z_ = func_240332_a_("template_rail_raised_sw", "_raised_sw", StockTextureAliases.field_240422_s_);
   public static final ModelsUtil field_240280_aa_ = func_240333_a_("carpet", StockTextureAliases.field_240423_t_);
   public static final ModelsUtil field_240281_ab_ = func_240333_a_("coral_fan", StockTextureAliases.field_240427_x_);
   public static final ModelsUtil field_240282_ac_ = func_240333_a_("coral_wall_fan", StockTextureAliases.field_240427_x_);
   public static final ModelsUtil field_240283_ad_ = func_240333_a_("template_glazed_terracotta", StockTextureAliases.field_240424_u_);
   public static final ModelsUtil field_240284_ae_ = func_240333_a_("template_chorus_flower", StockTextureAliases.field_240405_b_);
   public static final ModelsUtil field_240285_af_ = func_240333_a_("template_daylight_detector", StockTextureAliases.field_240409_f_, StockTextureAliases.field_240412_i_);
   public static final ModelsUtil field_240286_ag_ = func_240332_a_("template_glass_pane_noside", "_noside", StockTextureAliases.field_240425_v_);
   public static final ModelsUtil field_240287_ah_ = func_240332_a_("template_glass_pane_noside_alt", "_noside_alt", StockTextureAliases.field_240425_v_);
   public static final ModelsUtil field_240288_ai_ = func_240332_a_("template_glass_pane_post", "_post", StockTextureAliases.field_240425_v_, StockTextureAliases.field_240426_w_);
   public static final ModelsUtil field_240289_aj_ = func_240332_a_("template_glass_pane_side", "_side", StockTextureAliases.field_240425_v_, StockTextureAliases.field_240426_w_);
   public static final ModelsUtil field_240290_ak_ = func_240332_a_("template_glass_pane_side_alt", "_side_alt", StockTextureAliases.field_240425_v_, StockTextureAliases.field_240426_w_);
   public static final ModelsUtil field_240291_al_ = func_240333_a_("template_command_block", StockTextureAliases.field_240410_g_, StockTextureAliases.field_240411_h_, StockTextureAliases.field_240412_i_);
   public static final ModelsUtil field_240292_am_ = func_240333_a_("template_anvil", StockTextureAliases.field_240409_f_);
   public static final ModelsUtil[] field_240293_an_ = IntStream.range(0, 8).mapToObj((p_240335_0_) -> {
      return func_240332_a_("stem_growth" + p_240335_0_, "_stage" + p_240335_0_, StockTextureAliases.field_240428_y_);
   }).toArray((p_240331_0_) -> {
      return new ModelsUtil[p_240331_0_];
   });
   public static final ModelsUtil field_240294_ao_ = func_240333_a_("stem_fruit", StockTextureAliases.field_240428_y_, StockTextureAliases.field_240429_z_);
   public static final ModelsUtil field_240295_ap_ = func_240333_a_("crop", StockTextureAliases.field_240393_A_);
   public static final ModelsUtil field_240296_aq_ = func_240333_a_("template_farmland", StockTextureAliases.field_240394_B_, StockTextureAliases.field_240409_f_);
   public static final ModelsUtil field_240297_ar_ = func_240333_a_("template_fire_floor", StockTextureAliases.field_240395_C_);
   public static final ModelsUtil field_240298_as_ = func_240333_a_("template_fire_side", StockTextureAliases.field_240395_C_);
   public static final ModelsUtil field_240299_at_ = func_240333_a_("template_fire_side_alt", StockTextureAliases.field_240395_C_);
   public static final ModelsUtil field_240300_au_ = func_240333_a_("template_fire_up", StockTextureAliases.field_240395_C_);
   public static final ModelsUtil field_240301_av_ = func_240333_a_("template_fire_up_alt", StockTextureAliases.field_240395_C_);
   public static final ModelsUtil field_240302_aw_ = func_240333_a_("template_campfire", StockTextureAliases.field_240395_C_, StockTextureAliases.field_240401_I_);
   public static final ModelsUtil field_240303_ax_ = func_240333_a_("template_lantern", StockTextureAliases.field_240396_D_);
   public static final ModelsUtil field_240304_ay_ = func_240332_a_("template_hanging_lantern", "_hanging", StockTextureAliases.field_240396_D_);
   public static final ModelsUtil field_240305_az_ = func_240333_a_("template_torch", StockTextureAliases.field_240399_G_);
   public static final ModelsUtil field_240263_aA_ = func_240333_a_("template_torch_wall", StockTextureAliases.field_240399_G_);
   public static final ModelsUtil field_240264_aB_ = func_240333_a_("template_piston", StockTextureAliases.field_240397_E_, StockTextureAliases.field_240408_e_, StockTextureAliases.field_240412_i_);
   public static final ModelsUtil field_240265_aC_ = func_240333_a_("template_piston_head", StockTextureAliases.field_240397_E_, StockTextureAliases.field_240412_i_, StockTextureAliases.field_240398_F_);
   public static final ModelsUtil field_240266_aD_ = func_240333_a_("template_piston_head_short", StockTextureAliases.field_240397_E_, StockTextureAliases.field_240412_i_, StockTextureAliases.field_240398_F_);
   public static final ModelsUtil field_240267_aE_ = func_240333_a_("template_seagrass", StockTextureAliases.field_240405_b_);
   public static final ModelsUtil field_240268_aF_ = func_240333_a_("template_turtle_egg", StockTextureAliases.field_240404_a_);
   public static final ModelsUtil field_240269_aG_ = func_240333_a_("template_two_turtle_eggs", StockTextureAliases.field_240404_a_);
   public static final ModelsUtil field_240270_aH_ = func_240333_a_("template_three_turtle_eggs", StockTextureAliases.field_240404_a_);
   public static final ModelsUtil field_240271_aI_ = func_240333_a_("template_four_turtle_eggs", StockTextureAliases.field_240404_a_);
   public static final ModelsUtil field_240272_aJ_ = func_240333_a_("template_single_face", StockTextureAliases.field_240405_b_);
   public static final ModelsUtil field_240273_aK_ = func_240336_b_("generated", StockTextureAliases.field_240400_H_);
   public static final ModelsUtil field_240274_aL_ = func_240336_b_("handheld", StockTextureAliases.field_240400_H_);
   public static final ModelsUtil field_240275_aM_ = func_240336_b_("handheld_rod", StockTextureAliases.field_240400_H_);
   public static final ModelsUtil field_240276_aN_ = func_240336_b_("template_shulker_box", StockTextureAliases.field_240406_c_);
   public static final ModelsUtil field_240277_aO_ = func_240336_b_("template_bed", StockTextureAliases.field_240406_c_);
   public static final ModelsUtil field_240278_aP_ = func_240336_b_("template_banner");
   public static final ModelsUtil field_240279_aQ_ = func_240336_b_("template_skull");

   private static ModelsUtil func_240334_a_(StockTextureAliases... p_240334_0_) {
      return new ModelsUtil(Optional.empty(), Optional.empty(), p_240334_0_);
   }

   private static ModelsUtil func_240333_a_(String p_240333_0_, StockTextureAliases... p_240333_1_) {
      return new ModelsUtil(Optional.of(new ResourceLocation("minecraft", "block/" + p_240333_0_)), Optional.empty(), p_240333_1_);
   }

   private static ModelsUtil func_240336_b_(String p_240336_0_, StockTextureAliases... p_240336_1_) {
      return new ModelsUtil(Optional.of(new ResourceLocation("minecraft", "item/" + p_240336_0_)), Optional.empty(), p_240336_1_);
   }

   private static ModelsUtil func_240332_a_(String p_240332_0_, String p_240332_1_, StockTextureAliases... p_240332_2_) {
      return new ModelsUtil(Optional.of(new ResourceLocation("minecraft", "block/" + p_240332_0_)), Optional.of(p_240332_1_), p_240332_2_);
   }
}
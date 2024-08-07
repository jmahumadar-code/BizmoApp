package com.bizmolimited.bizapp.utils;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.bizmolimited.bizapp.R;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;



public class Country {
  public static final Country[] COUNTRIES = {
      new Country("AD", "Andorra", "+376", null, R.drawable.flag_ad,null, null, '.', ','),
      new Country("AE", "United Arab Emirates", "+971", null, R.drawable.flag_ae, null, null, '.', ','),
      new Country("AF", "Afghanistan", "+93", null, R.drawable.flag_af, null, null, '.', ','),
      new Country("AG", "Antigua and Barbuda", "+1", null, R.drawable.flag_ag, null, null, '.', ','),
      new Country("AI", "Anguilla", "+1", null, R.drawable.flag_ai, null, null, '.', ','),
      new Country("AL", "Albania", "+355", null, R.drawable.flag_al, null, null, '.', ','),
      new Country("AM", "Armenia", "+374", null, R.drawable.flag_am, null, null, '.', ','),
      new Country("AO", "Angola", "+244", null, R.drawable.flag_ao, null, null, '.', ','),
      new Country("AQ", "Antarctica", "+672", null, R.drawable.flag_aq, null, null, '.', ','),
      new Country("AR", "Argentina", "+54", null, R.drawable.flag_ar, null, null, '.', ','),
      new Country("AS", "AmericanSamoa", "+1", null, R.drawable.flag_as, null, null, '.', ','),
      new Country("AT", "Austria", "+43", null, R.drawable.flag_at, null, null, '.', ','),
      new Country("AU", "Australia", "+61", null, R.drawable.flag_au, null, null, '.', ','),
      new Country("AW", "Aruba", "+297", null, R.drawable.flag_aw, null, null, '.', ','),
      new Country("AX", "Åland Islands", "+358", null, R.drawable.flag_ax, null, null, '.', ','),
      new Country("AZ", "Azerbaijan", "+994", null, R.drawable.flag_az, null, null, '.', ','),
      new Country("BA", "Bosnia and Herzegovina", "+387", null, R.drawable.flag_ba, null, null, '.', ','),
      new Country("BB", "Barbados", "+1", null, R.drawable.flag_bb, null, null, '.', ','),
      new Country("BD", "Bangladesh", "+880", null, R.drawable.flag_bd, null, null, '.', ','),
      new Country("BE", "Belgium", "+32", null, R.drawable.flag_be, null, null, '.', ','),
      new Country("BF", "Burkina Faso", "+226", null, R.drawable.flag_bf, null, null, '.', ','),
      new Country("BG", "Bulgaria", "+359", null, R.drawable.flag_bg, null, null, '.', ','),
      new Country("BH", "Bahrain", "+973", null, R.drawable.flag_bh, null, null, '.', ','),
      new Country("BI", "Burundi", "+257", null, R.drawable.flag_bi, null, null, '.', ','),
      new Country("BJ", "Benin", "+229", null, R.drawable.flag_bj, null, null, '.', ','),
      new Country("BL", "Saint Barthélemy", "+590", null, R.drawable.flag_bl, null, null, '.', ','),
      new Country("BM", "Bermuda", "+1", null, R.drawable.flag_bm, null, null, '.', ','),
      new Country("BN", "Brunei Darussalam", "+673", null, R.drawable.flag_bn, null, null, '.', ','),
      new Country("BO", "Bolivia, Plurinational State of", "+591", null, R.drawable.flag_bo, null, null, '.', ','),
      new Country("BQ", "Bonaire", "+599", null, R.drawable.flag_bq, null, null, '.', ','),
      new Country("BR", "Brazil", "+55", null, R.drawable.flag_br, null, null, '.', ','),
      new Country("BS", "Bahamas", "+1", null, R.drawable.flag_bs, null, null, '.', ','),
      new Country("BT", "Bhutan", "+975", null, R.drawable.flag_bt, null, null, '.', ','),
      new Country("BV", "Bouvet Island", "+47", null, R.drawable.flag_bv, null, null, '.', ','),
      new Country("BW", "Botswana", "+267", null, R.drawable.flag_bw, null, null, '.', ','),
      new Country("BY", "Belarus", "+375", null, R.drawable.flag_by, null, null, '.', ','),
      new Country("BZ", "Belize", "+501", null, R.drawable.flag_bz, null, null, '.', ','),
      new Country("CA", "Canada", "+1", null, R.drawable.flag_ca, null, null, '.', ','),
      new Country("CC", "Cocos (Keeling) Islands", "+61", null, R.drawable.flag_cc, null, null, '.', ','),
      new Country("CD", "Congo, The Democratic Republic of the", "+243", null, R.drawable.flag_cd, null, null, '.', ','),
      new Country("CF", "Central African Republic", "+236", null, R.drawable.flag_cf, null, null, '.', ','),
      new Country("CG", "Congo", "+242", null, R.drawable.flag_cg, null, null, '.', ','),
      new Country("CH", "Switzerland", "+41", null, R.drawable.flag_ch, null, null, '.', ','),
      new Country("CI", "Ivory Coast", "+225", null, R.drawable.flag_ci, null, null, '.', ','),
      new Country("CK", "Cook Islands", "+682", null, R.drawable.flag_ck, null, null, '.', ','),
      new Country("CL", "Chile", "+56", "^(9)\\d{8}", R.drawable.flag_cl, "CLP $", "(?=.)^\\$?(([1-9][0-9]{0,2}(.[0-9]{3})*)|[0-9]+)?", '.', ','),
      new Country("CM", "Cameroon", "+237", null, R.drawable.flag_cm, null, null, '.', ','),
      new Country("CN", "China", "+86", null, R.drawable.flag_cn, null, null, '.', ','),
      new Country("CO", "Colombia", "+57", null, R.drawable.flag_co, null, null, '.', ','),
      new Country("CR", "Costa Rica", "+506", null, R.drawable.flag_cr, null, null, '.', ','),
      new Country("CU", "Cuba", "+53", null, R.drawable.flag_cu, null, null, '.', ','),
      new Country("CV", "Cape Verde", "+238", null, R.drawable.flag_cv, null, null, '.', ','),
      new Country("CW", "Curacao", "+599", null, R.drawable.flag_cw, null, null, '.', ','),
      new Country("CX", "Christmas Island", "+61", null, R.drawable.flag_cx, null, null, '.', ','),
      new Country("CY", "Cyprus", "+357", null, R.drawable.flag_cy, null, null, '.', ','),
      new Country("CZ", "Czech Republic", "+420", null, R.drawable.flag_cz, null, null, '.', ','),
      new Country("DE", "Germany", "+49", null, R.drawable.flag_de, null, null, '.', ','),
      new Country("DJ", "Djibouti", "+253", null, R.drawable.flag_dj, null, null, '.', ','),
      new Country("DK", "Denmark", "+45", null, R.drawable.flag_dk, null, null, '.', ','),
      new Country("DM", "Dominica", "+1", null, R.drawable.flag_dm, null, null, '.', ','),
      new Country("DO", "Dominican Republic", "+1", null, R.drawable.flag_do, null, null, '.', ','),
      new Country("DZ", "Algeria", "+213", null, R.drawable.flag_dz, null, null, '.', ','),
      new Country("EC", "Ecuador", "+593", null, R.drawable.flag_ec, null, null, '.', ','),
      new Country("EE", "Estonia", "+372", null, R.drawable.flag_ee, null, null, '.', ','),
      new Country("EG", "Egypt", "+20", null, R.drawable.flag_eg, null, null, '.', ','),
      new Country("EH", "Western Sahara", "+212", null, R.drawable.flag_eh, null, null, '.', ','),
      new Country("ER", "Eritrea", "+291", null, R.drawable.flag_er, null, null, '.', ','),
      new Country("ES", "Spain", "+34", null, R.drawable.flag_es, null, null, '.', ','),
      new Country("ET", "Ethiopia", "+251", null, R.drawable.flag_et, null, null, '.', ','),
      new Country("FI", "Finland", "+358", null, R.drawable.flag_fi, null, null, '.', ','),
      new Country("FJ", "Fiji", "+679", null, R.drawable.flag_fj, null, null, '.', ','),
      new Country("FK", "Falkland Islands (Malvinas)", "+500", null, R.drawable.flag_fk, null, null, '.', ','),
      new Country("FM", "Micronesia, Federated States of", "+691", null, R.drawable.flag_fm, null, null, '.', ','),
      new Country("FO", "Faroe Islands", "+298", null, R.drawable.flag_fo, null, null, '.', ','),
      new Country("FR", "France", "+33", null, R.drawable.flag_fr, null, null, '.', ','),
      new Country("GA", "Gabon", "+241", null, R.drawable.flag_ga, null, null, '.', ','),
      new Country("GB", "United Kingdom", "+44", null, R.drawable.flag_gb, null, null, '.', ','),
      new Country("GD", "Grenada", "+1", null, R.drawable.flag_gd, null, null, '.', ','),
      new Country("GE", "Georgia", "+995", null, R.drawable.flag_ge, null, null, '.', ','),
      new Country("GF", "French Guiana", "+594", null, R.drawable.flag_gf, null, null, '.', ','),
      new Country("GG", "Guernsey", "+44", null, R.drawable.flag_gg, null, null, '.', ','),
      new Country("GH", "Ghana", "+233", null, R.drawable.flag_gh, null, null, '.', ','),
      new Country("GI", "Gibraltar", "+350", null, R.drawable.flag_gi, null, null, '.', ','),
      new Country("GL", "Greenland", "+299", null, R.drawable.flag_gl, null, null, '.', ','),
      new Country("GM", "Gambia", "+220", null, R.drawable.flag_gm, null, null, '.', ','),
      new Country("GN", "Guinea", "+224", null, R.drawable.flag_gn, null, null, '.', ','),
      new Country("GP", "Guadeloupe", "+590", null, R.drawable.flag_gp, null, null, '.', ','),
      new Country("GQ", "Equatorial Guinea", "+240", null, R.drawable.flag_gq, null, null, '.', ','),
      new Country("GR", "Greece", "+30", null, R.drawable.flag_gr, null, null, '.', ','),
      new Country("GS", "South Georgia and the South Sandwich Islands", "+500", null, R.drawable.flag_gs, null, null, '.', ','),
      new Country("GT", "Guatemala", "+502", null, R.drawable.flag_gt, null, null, '.', ','),
      new Country("GU", "Guam", "+1", null, R.drawable.flag_gu, null, null, '.', ','),
      new Country("GW", "Guinea-Bissau", "+245", null, R.drawable.flag_gw, null, null, '.', ','),
      new Country("GY", "Guyana", "+595", null, R.drawable.flag_gy, null, null, '.', ','),
      new Country("HK", "Hong Kong", "+852", null, R.drawable.flag_hk, null, null, '.', ','),
      new Country("HM", "Heard Island and McDonald Islands", "", null, R.drawable.flag_hm, null, null, '.', ','),
      new Country("HN", "Honduras", "+504", null, R.drawable.flag_hn, null, null, '.', ','),
      new Country("HR", "Croatia", "+385", null, R.drawable.flag_hr, null, null, '.', ','),
      new Country("HT", "Haiti", "+509", null, R.drawable.flag_ht, null, null, '.', ','),
      new Country("HU", "Hungary", "+36", null, R.drawable.flag_hu, null, null, '.', ','),
      new Country("ID", "Indonesia", "+62", null, R.drawable.flag_id, null, null, '.', ','),
      new Country("IE", "Ireland", "+353", null, R.drawable.flag_ie, null, null, '.', ','),
      new Country("IL", "Israel", "+972", null, R.drawable.flag_il, null, null, '.', ','),
      new Country("IM", "Isle of Man", "+44", null, R.drawable.flag_im, null, null, '.', ','),
      new Country("IN", "India", "+91", null, R.drawable.flag_in, null, null, '.', ','),
      new Country("IO", "British Indian Ocean Territory", "+246", null, R.drawable.flag_io, null, null, '.', ','),
      new Country("IQ", "Iraq", "+964", null, R.drawable.flag_iq, null, null, '.', ','),
      new Country("IR", "Iran, Islamic Republic of", "+98", null, R.drawable.flag_ir, null, null, '.', ','),
      new Country("IS", "Iceland", "+354", null, R.drawable.flag_is, null, null, '.', ','),
      new Country("IT", "Italy", "+39", null, R.drawable.flag_it, null, null, '.', ','),
      new Country("JE", "Jersey", "+44", null, R.drawable.flag_je, null, null, '.', ','),
      new Country("JM", "Jamaica", "+1", null, R.drawable.flag_jm, null, null, '.', ','),
      new Country("JO", "Jordan", "+962", null, R.drawable.flag_jo, null, null, '.', ','),
      new Country("JP", "Japan", "+81", null, R.drawable.flag_jp, null, null, '.', ','),
      new Country("KE", "Kenya", "+254", null, R.drawable.flag_ke, null, null, '.', ','),
      new Country("KG", "Kyrgyzstan", "+996", null, R.drawable.flag_kg, null, null, '.', ','),
      new Country("KH", "Cambodia", "+855", null, R.drawable.flag_kh, null, null, '.', ','),
      new Country("KI", "Kiribati", "+686", null, R.drawable.flag_ki, null, null, '.', ','),
      new Country("KM", "Comoros", "+269", null, R.drawable.flag_km, null, null, '.', ','),
      new Country("KN", "Saint Kitts and Nevis", "+1", null, R.drawable.flag_kn, null, null, '.', ','),
      new Country("KP", "North Korea", "+850", null, R.drawable.flag_kp, null, null, '.', ','),
      new Country("KR", "South Korea", "+82", null, R.drawable.flag_kr, null, null, '.', ','),
      new Country("KW", "Kuwait", "+965", null, R.drawable.flag_kw, null, null, '.', ','),
      new Country("KY", "Cayman Islands", "+345", null, R.drawable.flag_ky, null, null, '.', ','),
      new Country("KZ", "Kazakhstan", "+7", null, R.drawable.flag_kz, null, null, '.', ','),
      new Country("LA", "Lao People's Democratic Republic", "+856", null, R.drawable.flag_la, null, null, '.', ','),
      new Country("LB", "Lebanon", "+961", null, R.drawable.flag_lb, null, null, '.', ','),
      new Country("LC", "Saint Lucia", "+1", null, R.drawable.flag_lc, null, null, '.', ','),
      new Country("LI", "Liechtenstein", "+423", null, R.drawable.flag_li, null, null, '.', ','),
      new Country("LK", "Sri Lanka", "+94", null, R.drawable.flag_lk, null, null, '.', ','),
      new Country("LR", "Liberia", "+231", null, R.drawable.flag_lr, null, null, '.', ','),
      new Country("LS", "Lesotho", "+266", null, R.drawable.flag_ls, null, null, '.', ','),
      new Country("LT", "Lithuania", "+370", null, R.drawable.flag_lt, null, null, '.', ','),
      new Country("LU", "Luxembourg", "+352", null, R.drawable.flag_lu, null, null, '.', ','),
      new Country("LV", "Latvia", "+371", null, R.drawable.flag_lv, null, null, '.', ','),
      new Country("LY", "Libyan Arab Jamahiriya", "+218", null, R.drawable.flag_ly, null, null, '.', ','),
      new Country("MA", "Morocco", "+212", null, R.drawable.flag_ma, null, null, '.', ','),
      new Country("MC", "Monaco", "+377", null, R.drawable.flag_mc, null, null, '.', ','),
      new Country("MD", "Moldova, Republic of", "+373", null, R.drawable.flag_md, null, null, '.', ','),
      new Country("ME", "Montenegro", "+382", null, R.drawable.flag_me, null, null, '.', ','),
      new Country("MF", "Saint Martin", "+590", null, R.drawable.flag_mf, null, null, '.', ','),
      new Country("MG", "Madagascar", "+261", null, R.drawable.flag_mg, null, null, '.', ','),
      new Country("MH", "Marshall Islands", "+692", null, R.drawable.flag_mh, null, null, '.', ','),
      new Country("MK", "Macedonia, The Former Yugoslav Republic of", "+389", null, R.drawable.flag_mk, null, null, '.', ','),
      new Country("ML", "Mali", "+223", null, R.drawable.flag_ml, null, null, '.', ','),
      new Country("MM", "Myanmar", "+95", null, R.drawable.flag_mm, null, null, '.', ','),
      new Country("MN", "Mongolia", "+976", null, R.drawable.flag_mn, null, null, '.', ','),
      new Country("MO", "Macao", "+853", null, R.drawable.flag_mo, null, null, '.', ','),
      new Country("MP", "Northern Mariana Islands", "+1", null, R.drawable.flag_mp, null, null, '.', ','),
      new Country("MQ", "Martinique", "+596", null, R.drawable.flag_mq, null, null, '.', ','),
      new Country("MR", "Mauritania", "+222", null, R.drawable.flag_mr, null, null, '.', ','),
      new Country("MS", "Montserrat", "+1", null, R.drawable.flag_ms, null, null, '.', ','),
      new Country("MT", "Malta", "+356", null, R.drawable.flag_mt, null, null, '.', ','),
      new Country("MU", "Mauritius", "+230", null, R.drawable.flag_mu, null, null, '.', ','),
      new Country("MV", "Maldives", "+960", null, R.drawable.flag_mv, null, null, '.', ','),
      new Country("MW", "Malawi", "+265", null, R.drawable.flag_mw, null, null, '.', ','),
      new Country("MX", "Mexico", "+52", null, R.drawable.flag_mx, null, null, '.', ','),
      new Country("MY", "Malaysia", "+60", null, R.drawable.flag_my, null, null, '.', ','),
      new Country("MZ", "Mozambique", "+258", null, R.drawable.flag_mz, null, null, '.', ','),
      new Country("NA", "Namibia", "+264", null, R.drawable.flag_na, null, null, '.', ','),
      new Country("NC", "New Caledonia", "+687", null, R.drawable.flag_nc, null, null, '.', ','),
      new Country("NE", "Niger", "+227", null, R.drawable.flag_ne, null, null, '.', ','),
      new Country("NF", "Norfolk Island", "+672", null, R.drawable.flag_nf, null, null, '.', ','),
      new Country("NG", "Nigeria", "+234", null, R.drawable.flag_ng, null, null, '.', ','),
      new Country("NI", "Nicaragua", "+505", null, R.drawable.flag_ni, null, null, '.', ','),
      new Country("NL", "Netherlands", "+31", null, R.drawable.flag_nl, null, null, '.', ','),
      new Country("NO", "Norway", "+47", null, R.drawable.flag_no, null, null, '.', ','),
      new Country("NP", "Nepal", "+977", null, R.drawable.flag_np, null, null, '.', ','),
      new Country("NR", "Nauru", "+674", null, R.drawable.flag_nr, null, null, '.', ','),
      new Country("NU", "Niue", "+683", null, R.drawable.flag_nu, null, null, '.', ','),
      new Country("NZ", "New Zealand", "+64", "^(2)[0-9]{1,3}\\d{6,8}$", R.drawable.flag_nz, "NZD $", "(?=.)^\\$?(([1-9][0-9]{0,2}(,[0-9]{3})*)|[0-9]+)?(\\.[0-9]{1,2})?$", ',', '.'),
      new Country("OM", "Oman", "+968", null, R.drawable.flag_om, null, null, '.', ','),
      new Country("PA", "Panama", "+507", null, R.drawable.flag_pa, null, null, '.', ','),
      new Country("PE", "Peru", "+51", "^(9)\\d{8}", R.drawable.flag_pe, "PEN $", "(?=.)^\\$?(([1-9][0-9]{0,2}(.[0-9]{3})*)|[0-9]+)?", '.', ','),
      new Country("PF", "French Polynesia", "+689", null, R.drawable.flag_pf, null, null, '.', ','),
      new Country("PG", "Papua New Guinea", "+675", null, R.drawable.flag_pg, null, null, '.', ','),
      new Country("PH", "Philippines", "+63", null, R.drawable.flag_ph, null, null, '.', ','),
      new Country("PK", "Pakistan", "+92", null, R.drawable.flag_pk, null, null, '.', ','),
      new Country("PL", "Poland", "+48", null, R.drawable.flag_pl, null, null, '.', ','),
      new Country("PM", "Saint Pierre and Miquelon", "+508", null, R.drawable.flag_pm, null, null, '.', ','),
      new Country("PN", "Pitcairn", "+872", null, R.drawable.flag_pn, null, null, '.', ','),
      new Country("PR", "Puerto Rico", "+1", null, R.drawable.flag_pr, null, null, '.', ','),
      new Country("PS", "Palestinian Territory, Occupied", "+970", null, R.drawable.flag_ps, null, null, '.', ','),
      new Country("PT", "Portugal", "+351", null, R.drawable.flag_pt, null, null, '.', ','),
      new Country("PW", "Palau", "+680", null, R.drawable.flag_pw, null, null, '.', ','),
      new Country("PY", "Paraguay", "+595", null, R.drawable.flag_py, null, null, '.', ','),
      new Country("QA", "Qatar", "+974", null, R.drawable.flag_qa, null, null, '.', ','),
      new Country("RE", "Réunion", "+262", null, R.drawable.flag_re, null, null, '.', ','),
      new Country("RO", "Romania", "+40", null, R.drawable.flag_ro, null, null, '.', ','),
      new Country("RS", "Serbia", "+381", null, R.drawable.flag_rs, null, null, '.', ','),
      new Country("RU", "Russia", "+7", null, R.drawable.flag_ru, null, null, '.', ','),
      new Country("RW", "Rwanda", "+250", null, R.drawable.flag_rw, null, null, '.', ','),
      new Country("SA", "Saudi Arabia", "+966", null, R.drawable.flag_sa, null, null, '.', ','),
      new Country("SB", "Solomon Islands", "+677", null, R.drawable.flag_sb, null, null, '.', ','),
      new Country("SC", "Seychelles", "+248", null, R.drawable.flag_sc, null, null, '.', ','),
      new Country("SD", "Sudan", "+249", null, R.drawable.flag_sd, null, null, '.', ','),
      new Country("SE", "Sweden", "+46", null, R.drawable.flag_se, null, null, '.', ','),
      new Country("SG", "Singapore", "+65", null, R.drawable.flag_sg, null, null, '.', ','),
      new Country("SH", "Saint Helena, Ascension and Tristan Da Cunha", "+290", null, R.drawable.flag_sh, null, null, '.', ','),
      new Country("SI", "Slovenia", "+386", null, R.drawable.flag_si, null, null, '.', ','),
      new Country("SJ", "Svalbard and Jan Mayen", "+47", null, R.drawable.flag_sj, null, null, '.', ','),
      new Country("SK", "Slovakia", "+421", null, R.drawable.flag_sk, null, null, '.', ','),
      new Country("SL", "Sierra Leone", "+232", null, R.drawable.flag_sl, null, null, '.', ','),
      new Country("SM", "San Marino", "+378", null, R.drawable.flag_sm, null, null, '.', ','),
      new Country("SN", "Senegal", "+221", null, R.drawable.flag_sn, null, null, '.', ','),
      new Country("SO", "Somalia", "+252", null, R.drawable.flag_so, null, null, '.', ','),
      new Country("SR", "Suriname", "+597", null, R.drawable.flag_sr, null, null, '.', ','),
      new Country("SS", "South Sudan", "+211", null, R.drawable.flag_ss,null, null, '.', ','),
      new Country("ST", "Sao Tome and Principe", "+239", null, R.drawable.flag_st, null, null, '.', ','),
      new Country("SV", "El Salvador", "+503", null, R.drawable.flag_sv, null, null, '.', ','),
      new Country("SX", "  Sint Maarten", "+1", null, R.drawable.flag_sx, null, null, '.', ','),
      new Country("SY", "Syrian Arab Republic", "+963", null, R.drawable.flag_sy, null, null, '.', ','),
      new Country("SZ", "Swaziland", "+268", null, R.drawable.flag_sz, null, null, '.', ','),
      new Country("TC", "Turks and Caicos Islands", "+1", null, R.drawable.flag_tc, null, null, '.', ','),
      new Country("TD", "Chad", "+235", null, R.drawable.flag_td, null, null, '.', ','),
      new Country("TF", "French Southern Territories", "+262", null, R.drawable.flag_tf, null, null, '.', ','),
      new Country("TG", "Togo", "+228", null, R.drawable.flag_tg, null, null, '.', ','),
      new Country("TH", "Thailand", "+66", null, R.drawable.flag_th, null, null, '.', ','),
      new Country("TJ", "Tajikistan", "+992", null, R.drawable.flag_tj, null, null, '.', ','),
      new Country("TK", "Tokelau", "+690", null, R.drawable.flag_tk, null, null, '.', ','),
      new Country("TL", "East Timor", "+670", null, R.drawable.flag_tl, null, null, '.', ','),
      new Country("TM", "Turkmenistan", "+993", null, R.drawable.flag_tm, null, null, '.', ','),
      new Country("TN", "Tunisia", "+216", null, R.drawable.flag_tn, null, null, '.', ','),
      new Country("TO", "Tonga", "+676", null, R.drawable.flag_to, null, null, '.', ','),
      new Country("TR", "Turkey", "+90", null, R.drawable.flag_tr, null, null, '.', ','),
      new Country("TT", "Trinidad and Tobago", "+1", null, R.drawable.flag_tt, null, null, '.', ','),
      new Country("TV", "Tuvalu", "+688", null, R.drawable.flag_tv, null, null, '.', ','),
      new Country("TW", "Taiwan", "+886", null, R.drawable.flag_tw, null, null, '.', ','),
      new Country("TZ", "Tanzania, United Republic of", "+255", null, R.drawable.flag_tz, null, null, '.', ','),
      new Country("UA", "Ukraine", "+380", null, R.drawable.flag_ua, null, null, '.', ','),
      new Country("UG", "Uganda", "+256", null, R.drawable.flag_ug, null, null, '.', ','),
      new Country("UM", "U.S. Minor Outlying Islands", "", null, R.drawable.flag_um, null, null, '.', ','),
      new Country("US", "United States", "+1", null, R.drawable.flag_us, null, null, '.', ','),
      new Country("UY", "Uruguay", "+598", null, R.drawable.flag_uy, null, null, '.', ','),
      new Country("UZ", "Uzbekistan", "+998", null, R.drawable.flag_uz, null, null, '.', ','),
      new Country("VA", "Holy See (Vatican City State)", "+379", null, R.drawable.flag_va, null, null, '.', ','),
      new Country("VC", "Saint Vincent and the Grenadines", "+1", null, R.drawable.flag_vc, null, null, '.', ','),
      new Country("VE", "Venezuela, Bolivarian Republic of", "+58", null, R.drawable.flag_ve, null, null, '.', ','),
      new Country("VG", "Virgin Islands, British", "+1", null, R.drawable.flag_vg, null, null, '.', ','),
      new Country("VI", "Virgin Islands, U.S.", "+1", null, R.drawable.flag_vi, null, null, '.', ','),
      new Country("VN", "Viet Nam", "+84", null, R.drawable.flag_vn, null, null, '.', ','),
      new Country("VU", "Vanuatu", "+678", null, R.drawable.flag_vu, null, null, '.', ','),
      new Country("WF", "Wallis and Futuna", "+681", null, R.drawable.flag_wf, null, null, '.', ','),
      new Country("WS", "Samoa", "+685", null, R.drawable.flag_ws, null, null, '.', ','),
      new Country("XK", "Kosovo", "+383", null, R.drawable.flag_xk, null, null, '.', ','),
      new Country("YE", "Yemen", "+967", null, R.drawable.flag_ye, null, null, '.', ','),
      new Country("YT", "Mayotte", "+262", null, R.drawable.flag_yt, null, null, '.', ','),
      new Country("ZA", "South Africa", "+27", null, R.drawable.flag_za, null, null, '.', ','),
      new Country("ZM", "Zambia", "+260", null, R.drawable.flag_zm, null, null, '.', ','),
      new Country("ZW", "Zimbabwe", "+263", null, R.drawable.flag_zw, null, null, '.', ','),
  };

  private String code;
  private String name;
  private String dialCode;
  private String regexPhone;
  private String signMoney = "$";
  private String regexCurrency;
  private char separatorMiles;
  private char separatorDecimal;
  private int flag = -1;

  public Country(String code, String name, String dialCode, String regexPhone, int flag, String sign, String currency, char separatorMiles, char separatorDecimal) {
    this.code = code;
    this.name = name;
    this.dialCode = dialCode;
    this.regexPhone = regexPhone;
    this.signMoney = sign;
    this.flag = flag;
    this.regexCurrency = currency;
    this.separatorMiles = separatorMiles;
    this.separatorDecimal = separatorDecimal;
  }

  public Country() {
  }

  public char getSeparatorDecimal() {
    return separatorDecimal;
  }

  public char getSeparatorMiles() {
    return separatorMiles;
  }

  public String getSignMoney() {
    return signMoney;
  }

  public void setSignMoney(String signMoney) {
    this.signMoney = signMoney;
  }

  public String getRegexPhone(){
    return regexPhone;
  }

  public void setRegexPhone(String regexPhone){
    this.regexPhone = regexPhone;
  }

  public String getDialCode() {
    return dialCode;
  }

  public void setDialCode(String dialCode) {
    this.dialCode = dialCode;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
    if (TextUtils.isEmpty(name)) {
      name = new Locale("", code).getDisplayName();
    }
  }

  public String getName() {
    return name;
  }

  public int getFlag() {
    return flag;
  }

  public String getRegexCurrency() {
    return regexCurrency;
  }

  public void setFlag(int flag) {
    this.flag = flag;
  }

  public void loadFlagByCode(Context context) {
    if (this.flag != -1)
      return;

    try {
      this.flag = context.getResources()
          .getIdentifier("flag_" + this.code.toLowerCase(Locale.ENGLISH), "drawable",
              context.getPackageName());
    } catch (Exception e) {
      e.printStackTrace();
      this.flag = -1;
    }
  }


    /*
     *      GENERIC STATIC FUNCTIONS
     */

  private static List<Country> allCountriesList;

  public static List<Country> getAllCountries() {
    if (allCountriesList == null) {
      allCountriesList = Arrays.asList(COUNTRIES);
    }
    return allCountriesList;
  }

  public static Country getCountryByISO(String countryIsoCode) {
    countryIsoCode = countryIsoCode.toUpperCase();

    Country c = new Country();
    c.setCode(countryIsoCode);

    int i = Arrays.binarySearch(COUNTRIES, c, new ISOCodeComparator());

    if (i < 0) {
      return null;
    } else {
      return COUNTRIES[i];
    }
  }

  public static Country getCountryByName(String countryName) {
    // Because the data we have is sorted by ISO codes and not by names, we must check all
    // countries one by one

    for (Country c : COUNTRIES) {
      if (countryName.equals(c.getName())) {
        return c;
      }
    }
    return null;
  }

  public static Country getCountryByLocale(Locale locale) {
    String countryIsoCode = locale.getISO3Country().substring(0, 2).toLowerCase();
    return Country.getCountryByISO(countryIsoCode);
  }

  public static Country getCountryFromSIM(Context context) {
    TelephonyManager telephonyManager =
        (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    if (telephonyManager.getSimState() != TelephonyManager.SIM_STATE_ABSENT) {
      return Country.getCountryByISO(telephonyManager.getSimCountryIso());
    }
    return null;
  }


  public static class ISOCodeComparator implements Comparator<Country> {
    @Override
    public int compare(Country country, Country t1) {
      return country.code.compareTo(t1.code);
    }
  }


  public static class NameComparator implements Comparator<Country> {
    @Override
    public int compare(Country country, Country t1) {
      return country.name.compareTo(t1.name);
    }
  }
}

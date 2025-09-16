package com.s_giken.training.webapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.s_giken.training.webapp.model.ChargeSearchCondition;
import com.s_giken.training.webapp.model.entity.Charge;
import com.s_giken.training.webapp.exception.NotFoundException;
import com.s_giken.training.webapp.service.ChargeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

/**
 * 理宇金情報管理コントローラクラス
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/charge")
public class ChargeController {
    private final ChargeService chargeService;

    /**
     * 料金情報検索条件入力画面を表示する
     * 
     * @param model テンプレートに渡す情報
     * @return テンプレート名
     */
    @GetMapping("/search")
    public String showSearchCondition(Model model) {
        var chargeSearchCondition = new ChargeSearchCondition();
        model.addAttribute("chargeSearchCondition", chargeSearchCondition);
        return "charge_search_condition";
    }

    /**
     * 料金情報検索結果画面を表示する
     * 
     * @param model テンプレートに渡す情報
     * @return テンプレート名
     */
    @PostMapping("/search")
    public String searchAndListing(
            @ModelAttribute("chargeSearchCondition") ChargeSearchCondition chargeSearchCodition,
            Model model) {
        var result = chargeService.findByConditions(chargeSearchCodition);
        model.addAttribute("result", result);
        return "charge_search_result";
    }

    /**
     * 料金情報追加画面を表示する
     * 
     * @param model テンプレートに渡す情報
     * @return テンプレート名
     */
    @GetMapping("/add")
    public String addCharge(Model model) {
        var charge = new Charge();
        model.addAttribute("charge", charge);
        model.addAttribute("addMode", true);
        return "charge_edit";
    }

    /**
     * 料金情報を追加する
     * 
     * @param charge 料金情報
     * @param bindingResult バリデーション結果
     * @param redirectAttributes リダイレクト時に渡す情報
     * @param model テンプレートに渡す情報
     * @return テンプレート名 or リダイレクト先
     */
    @PostMapping("/add")
    public String addCharge(
            @Validated Charge charge,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("addMode", true);
            model.addAttribute("charge", charge);
            return "charge_edit";
        }
        chargeService.add(charge);
        redirectAttributes.addFlashAttribute("message", "保存しました。");
        return "redirect:/charge/edit/" + charge.getChargeId();
    }

    /**
     * 料金情報編集画面を表示する
     * 
     * @param id 編集対象の料金情報ID(URLより取得)
     * @param model テンプレートに渡す情報
     * @return テンプレート名
     */
    @GetMapping("/edit/{id}")
    public String editCharge(
            @PathVariable Long id,
            Model model) {
        var charge = chargeService.findById(id);
        if (!charge.isPresent()) {
            throw new NotFoundException("");
        }
        model.addAttribute("addMode", false);
        model.addAttribute("charge", charge.get());
        return "charge_edit";

    }

    /**
     * 料金情報を更新する
     * 
     * @param charge 料金情報
     * @param bindingResult バリデーション結果
     * @param redirectAttributes リダイレクト先に渡す情報を格納するオブジェクト
     * @param model テンプレートに渡す情報を格納するオブジェクト
     * @return テンプレート名 or リダイレクト先
     */
    @PostMapping("/update")
    public String updateCharge(
            @Validated Charge charge,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("addMode", false);
            model.addAttribute("charge", charge);
            return "charge_edit";
        }
        chargeService.update(charge);
        redirectAttributes.addFlashAttribute("message", "保存しました。");
        return "redirect:/charge/edit/" + charge.getChargeId();
    }

    /**
     * 料金情報を削除する
     * 
     * @param id 削除対象となる料金情報ID
     * @param redirectAttributes リダイレクト先に渡す情報を格納するオブジェクト
     * @return リダイレクト先
     */
    @GetMapping("/delete/{id}")
    public String deleteCharge(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        chargeService.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "削除しました。");
        return "redirect:/charge/search";
    }

}

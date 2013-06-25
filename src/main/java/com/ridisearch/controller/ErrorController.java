//package com.ridisearch.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * Created with IntelliJ IDEA.
// * User: Abhinayak Swar
// * Date: 5/25/13
// * Time: 11:13 AM
// */
//@Controller
//@RequestMapping("/error")
//public class ErrorController {
//
//    @RequestMapping(value="/index",method = RequestMethod.GET)
//    public String index(RedirectAttributes attributes) {
////        req.setAttribute("message", "Something went wrong while processing your request. " +
////                "Please contact your Administrator!");
////        System.out.println("req = " + "something went wrong while processing your request. ");
////        ModelAndView modelAndView = new ModelAndView();
////        modelAndView.setViewName("error");
////        modelAndView.addObject("message","Something went wrong while processing your request. " +
////                "Please contact your Administrator!");
////        return modelAndView;
//        String message = "Something went wrong while processing your request.Please contact your Administrator!";
//        attributes.addFlashAttribute("message",message);
//        return "error/index";
//
//    }
//
//}

package com.jacksteam.googol.service;

import java.util.ArrayList;

import com.jacksteam.googol.kmap.KMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainParseService{
    private final SearchService searchService;
    private final ListElementService listElementService;
    private final CalculatorService calculatorService;
    private final ExchangeService exchangeService;
    private final BinomialService binomialService;
    private final KMap kmapService;

    @Autowired
    public MainParseService(SearchService searchService, ListElementService listElementService, CalculatorService calculatorService, ExchangeService exchangeService,BinomialService binomialService,KMap kmapService){
        this.searchService=searchService;
        this.listElementService=listElementService;
        this.calculatorService=calculatorService;
        this.exchangeService=exchangeService;
        this.binomialService = binomialService;
        this.kmapService = kmapService;
    }

    public String parse(String query){
        String[] split = query.split(" ",2);
        String firstWord = split[0];
        System.out.println("<<"+firstWord+">>");
        String out="";
        if(firstWord.length()==1){
            //Calculator
            if(firstWord.equalsIgnoreCase("c")){
                String temp="";
                for(int i=1;i<split.length;i++){
                    temp += split[i];
                }
                out += "<div class=\"container\"><h1>"+calculatorService.calculate(temp)+"</h1></div>";
                return out;
            }
            //Kmap and truth table
            else if(firstWord.equalsIgnoreCase("k")){
                kmapService.resultreset();
                try {
                    kmapService.parse(split[1]);
                } catch (Exception e) {
                    return "<div class=\"container\"><h1>Wrong input format! Please refer documentation.</h1></div>";
                }
                
                ArrayList<String> uwu = kmapService.getResult();
                out+="<style>.innercontainer div{padding:2em;} .innercontainer{display:grid;grid-template-columns:1fr 1fr;} .container{text-align:center;}";
                out +="#kmap h2 {background-color: #80cbc4;border: 1px solid #fff;font-size: 30px;text-align: center;color:#fff} #kmap h1 {color:#1e5631;background-color:#a4de02;border: 1px solid #fff;text-align:center;}";
                out +="#TT h2 {background-color: #80cbc4;border: 1px solid #fff;font-size: 30px;text-align: center;color:#fff} #TT h1 {color:#1e5631;background-color:#a4de02;border: 1px solid #fff;text-align:center;}";
                out +="</style>";
                for(String i:uwu){
                    out += i;
                }
                //out += "<div class=\"container\" text-align:center><h1>"+uwu+"</h1></div>";
                return out;
            }
            //Die Roll
            else if(firstWord.equalsIgnoreCase("r")){
                String uwu="";
                try {
                    uwu += (int)Math.floor(Math.random()*Integer.valueOf(split[1])+1);
                } catch (Exception e) {
                    uwu += (int)Math.floor(Math.random()*6+1);
                }
                out += "<style>.container{text-align:center} .container h1{font-size:200px}</style>";
                out += "<div class=\"container\"><h1>"+uwu+"</h1></div>";
                return out;
            }
            //Currency Exchange
            else if(firstWord.equalsIgnoreCase("x")){
                String[] uwu = split[1].split(" ");
                if(uwu.length<4){return "<div class=\"container\"><h1>"+"Please input in the format: x %amount% %currency1% to %currency2%"+"</h1></div>";}
                out += "<div class=\"container\"><h1>"+exchangeService.exch(uwu[0], uwu[1], uwu[3])+"</h1></div>";
                return out;
            }
            //Search
            else if(firstWord.equalsIgnoreCase("s")){
                ArrayList<Integer> temp = searchService.searchParse(split[1]);
                if(temp != null){
                    for(int i: temp){
                        out += listElementService.markupify(listElementService.genFromIndex(i));
                    }
                }else{
                    out += "<div class=\"container\"><h1>So much NOTHING!</h1><p>The search terms do not appear in any of the top 2000 sites.</p></div>";
                }
            }
            else if(firstWord.equalsIgnoreCase("b")){
                String temp="";
                for(int i=1;i<split.length;i++){
                    temp += split[i];
                }
                out += "<div class=\"container\"><h1>"+binomialService.parse(temp)+"</h1></div>";
                return out;
            }
            else{
                ArrayList<Integer> temp = searchService.searchParse(query);
                if(temp != null){
                    for(int i: temp){
                        out += listElementService.markupify(listElementService.genFromIndex(i));
                    }
                }else{
                    out += "<div class=\"container\"><h1>So much NOTHING!</h1><p>The search terms do not appear in any of the top 2000 sites.</p></div>";
                }
            }
        }else{
            ArrayList<Integer> temp = searchService.searchParse(query);
            if(temp != null){
                for(int i: temp){
                    out += listElementService.markupify(listElementService.genFromIndex(i));
                }
            }else{
                out += "<div class=\"container\"><h1>So much NOTHING!</h1><p>The search terms do not appear in any of the top 2000 sites.</p></div>";
            }

        }

        return out;
    }

}
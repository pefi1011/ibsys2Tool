package de.supplyTool.util;

import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import org.primefaces.component.themeswitcher.ThemeSwitcher;

import javax.faces.bean.SessionScoped;
 
@ManagedBean
@SessionScoped
public class ThemeSwitcherView {
 
    private List<Theme> themes;
    
    private String theme= "delta"; // default thema
    
    public String getTheme() {
        return theme;
     }

     public void setTheme(String theme) {
        this.theme = theme;
     }

     
    @ManagedProperty("#{themeService}")
    private ThemeService service;
 
    @PostConstruct
    public void init() {
        themes = service.getThemes();
    }
     
    public List<Theme> getThemes() {
        return themes;
    } 
 
    public void setService(ThemeService service) {
        this.service = service;
    }
    
    public void saveTheme(AjaxBehaviorEvent ajax)
    {
       setTheme((String) ((ThemeSwitcher)ajax.getSource()).getValue());
    }
}
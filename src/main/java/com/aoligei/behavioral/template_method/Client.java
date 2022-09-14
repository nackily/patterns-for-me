package com.aoligei.behavioral.template_method;

import com.aoligei.behavioral.template_method.domicile.EducationEntryDomicile;
import com.aoligei.behavioral.template_method.domicile.HouseEntryDomicile;
import com.aoligei.behavioral.template_method.domicile.RelativesEntryDomicile;

/**
 * Client
 *
 * @author coder
 * @date 2022-05-27 09:53:54
 * @since 1.0.0
 */
public class Client {

    public static void main(String[] args) {
        AbstractDomicile domicile4Tom = new EducationEntryDomicile("Tom");
        domicile4Tom.migrate();
        AbstractDomicile domicile4Jack = new HouseEntryDomicile("Jack");
        domicile4Jack.migrate();
        AbstractDomicile domicile4Lisa = new RelativesEntryDomicile("Lisa");
        domicile4Lisa.migrate();
    }
}

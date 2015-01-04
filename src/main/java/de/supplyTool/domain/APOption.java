package de.supplyTool.domain;

import java.io.Serializable;

public class APOption implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8127155782620204326L;
    String                    id;
    String                    optionOne;
    String                    optionTwo;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getOption() {
        return optionOne;
    }

    public void setOptionOne(final String option) {
        this.optionOne = option;
    }

    public String getOptionTwo() {
        return optionTwo;
    }

    public void setOptionTwo(final String optionTwo) {
        this.optionTwo = optionTwo;
    }

    public String getOptionOne() {
        return optionOne;
    }
}

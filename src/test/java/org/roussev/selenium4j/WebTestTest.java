package org.roussev.selenium4j;

import junit.framework.TestCase;

public class WebTestTest extends TestCase {
    private WebTest webTest = new WebTest();

    public void testShouldFail_WhenVerifyEquals_AndStringNotEqualToOtherString() throws Exception {
        try {
            webTest.verifyEquals("a", "b");
            fail("Expected to fail as a!=b");
        } catch(IllegalStateException ex) {

        }
    }

    public void testShouldFail_WhenVerifyEquals_AndNullNotEqualToOtherString() throws Exception {
        try {
            webTest.verifyEquals(null, "b");
            fail("Expected to fail as null!=b");
        } catch(IllegalStateException ex) {

        }
    }

    public void testShouldFail_WhenVerifyEquals_AndStringNotEqualToOtherNull() throws Exception {
        try {
            webTest.verifyEquals("a", null);
            fail("Expected to fail as a!=null");
        } catch(IllegalStateException ex) {

        }
    }

    public void testShouldPass_WhenVerifyEquals_AndBothNull() throws Exception {
        webTest.verifyEquals(null, null);
    }

    public void testShouldPass_WhenVerifyEquals_AndBothEqualStrings() throws Exception {
        webTest.verifyEquals("a", "a");
    }

    public void testShouldPass_WhenVerifyEquals_AndBothEqualObjects() throws Exception {
        Object o = new Object();
        Object ref = o;
        webTest.verifyEquals(o, ref);
    }

}
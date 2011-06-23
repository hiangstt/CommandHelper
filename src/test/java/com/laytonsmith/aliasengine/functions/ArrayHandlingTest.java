/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.laytonsmith.aliasengine.functions;

import com.laytonsmith.aliasengine.Constructs.Construct;
import com.laytonsmith.aliasengine.CancelCommandException;
import com.laytonsmith.aliasengine.ConfigCompileException;
import com.laytonsmith.aliasengine.ConfigRuntimeException;
import com.laytonsmith.aliasengine.Constructs.CArray;
import com.laytonsmith.aliasengine.Constructs.CInt;
import com.laytonsmith.testing.C;
import org.bukkit.entity.Player;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;
import static com.laytonsmith.testing.StaticTest.*;

/**
 *
 * @author Layton
 */
public class ArrayHandlingTest {

    static Player fakePlayer;
    static CArray commonArray;

    public ArrayHandlingTest() {
    }

    @Before
    public void setUp() {
        fakePlayer = mock(Player.class);
        commonArray = new CArray(0, new CInt(1, 0), new CInt(2, 0), new CInt(3, 0));
    }

    /**
     * Test of docs method, of class ArrayHandling.
     */
    @Test
    public void testDocs() {
        TestClassDocs(ArrayHandling.docs(), ArrayHandling.class);
    }

    @Test
    public void testArraySize() throws ConfigCompileException, CancelCommandException {
        ArrayHandling.array_size a = new ArrayHandling.array_size();
        TestBoilerplate(a, "array_size");
        CArray arr = commonArray;
        Construct ret = a.exec(0, fakePlayer, arr);
        assertReturn(ret, C.Int);
        assertCEquals(C.onstruct(3), ret);
    }

    @Test
    public void testArraySet() throws CancelCommandException {
        ArrayHandling.array_set a = new ArrayHandling.array_set();
        TestBoilerplate(a, "array_set");

        assertReturn(a.exec(0, fakePlayer, commonArray, C.Int(1), C.String("hi")), C.Void);

        //it should affect the 1-index element, but no others
        assertCEquals(C.onstruct(1), commonArray.get(0));
        assertCEquals(C.onstruct("hi"), commonArray.get(1));
        assertCEquals(C.onstruct(3), commonArray.get(2));
    }

    @Test
    public void testArrayContains() throws CancelCommandException {
        ArrayHandling.array_contains a = new ArrayHandling.array_contains();
        TestBoilerplate(a, "array_contains");
        assertCEquals(C.onstruct(true), a.exec(0, fakePlayer, commonArray, C.onstruct(1)));
        assertCEquals(C.onstruct(false), a.exec(0, fakePlayer, commonArray, C.onstruct(55)));
    }

    @Test
    public void testArrayGet() throws CancelCommandException {
        ArrayHandling.array_get a = new ArrayHandling.array_get();
        TestBoilerplate(a, "array_get");
        assertCEquals(C.onstruct(1), a.exec(0, fakePlayer, commonArray, C.onstruct(0)));
    }

    @Test(expected = ConfigRuntimeException.class)
    public void testArrayGetBad() throws CancelCommandException {
        ArrayHandling.array_get a = new ArrayHandling.array_get();
        a.exec(0, fakePlayer, commonArray, C.onstruct(55));
    }

    @Test
    public void testArrayPush() throws CancelCommandException {
        ArrayHandling.array_push a = new ArrayHandling.array_push();
        TestBoilerplate(a, "array_push");
        assertReturn(a.exec(0, fakePlayer, commonArray, C.onstruct(4)), C.Void);
        assertCEquals(C.onstruct(1), commonArray.get(0));
        assertCEquals(C.onstruct(2), commonArray.get(1));
        assertCEquals(C.onstruct(3), commonArray.get(2));
        assertCEquals(C.onstruct(4), commonArray.get(3));
    }
}
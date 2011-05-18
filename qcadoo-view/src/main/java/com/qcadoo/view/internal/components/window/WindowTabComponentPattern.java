/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo Framework
 * Version: 0.4.1
 *
 * This file is part of Qcadoo.
 *
 * Qcadoo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * ***************************************************************************
 */
package com.qcadoo.view.internal.components.window;

import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.qcadoo.view.api.ComponentState;
import com.qcadoo.view.internal.ComponentDefinition;
import com.qcadoo.view.internal.patterns.AbstractContainerPattern;
import com.qcadoo.view.internal.ribbon.InternalRibbon;
import com.qcadoo.view.internal.ribbon.RibbonUtils;
import com.qcadoo.view.internal.xml.ViewDefinitionParser;
import com.qcadoo.view.internal.xml.ViewDefinitionParserNodeException;

public class WindowTabComponentPattern extends AbstractContainerPattern {

    private static final String JSP_PATH = "containers/windowTab.jsp";

    private static final String JS_OBJECT = "QCD.components.containers.WindowTab";

    private InternalRibbon ribbon;

    public WindowTabComponentPattern(final ComponentDefinition componentDefinition) {
        super(componentDefinition);
    }

    @Override
    public ComponentState getComponentStateInstance() {
        return new WindowTabComponentState();
    }

    @Override
    public void parse(final Node componentNode, final ViewDefinitionParser parser) throws ViewDefinitionParserNodeException {
        super.parse(componentNode, parser);

        NodeList childNodes = componentNode.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node child = childNodes.item(i);
            if ("ribbon".equals(child.getNodeName())) {
                setRibbon(RibbonUtils.getInstance().parseRibbon(child, parser, getViewDefinition()));
                break;
            }
        }
    }

    @Override
    protected JSONObject getJsOptions(final Locale locale) throws JSONException {
        JSONObject json = new JSONObject();
        if (ribbon != null) {
            json.put("ribbon", RibbonUtils.getInstance().translateRibbon(ribbon, locale, this));
        }
        return json;
    }

    public void setRibbon(final InternalRibbon ribbon) {
        this.ribbon = ribbon;
    }

    @Override
    public String getJspFilePath() {
        return JSP_PATH;
    }

    @Override
    public String getJsFilePath() {
        return JS_PATH;
    }

    @Override
    public String getJsObjectName() {
        return JS_OBJECT;
    }
}
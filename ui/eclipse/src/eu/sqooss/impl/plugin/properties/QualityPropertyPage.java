/*
 * This file is part of the Alitheia system, developed by the SQO-OSS
 * consortium as part of the IST FP6 SQO-OSS project, number 033331.
 *
 * Copyright 2007-2008 by the SQO-OSS consortium members <info@sqo-oss.eu>
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     * Redistributions in binary form must reproduce the above
 *       copyright notice, this list of conditions and the following
 *       disclaimer in the documentation and/or other materials provided
 *       with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package eu.sqooss.impl.plugin.properties;

import org.eclipse.core.resources.IResource;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Link;
import org.eclipse.ui.preferences.IWorkbenchPreferenceContainer;

import eu.sqooss.plugin.util.Constants;

public class QualityPropertyPage extends AbstractQualityPropertyPage implements SelectionListener {

    private Composite parent;
    
    /**
     * @see eu.sqooss.plugin.properties.AbstractQualityPropertyPage#createContents(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected Control createContents(Composite parent) {
        this.parent = parent;
        mainControl = (Composite) super.createContents(parent);
        buttonCompareVersion.addSelectionListener(this);
        textFieldEntityPath.setText(getEntityPath());
        parent.forceFocus();
        enableIfPossible();
        return mainControl;
    }

    /**
     * @see org.eclipse.jface.preference.PreferencePage#createControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createControl(Composite parent) {
        noDefaultAndApplyButton();
        super.createControl(parent);
    }

    /**
     * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetDefaultSelected(SelectionEvent e) {
        //do nothing
    }

    /**
     * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
     */
    public void widgetSelected(SelectionEvent e) {
        Object eventSource = e.getSource(); 
        if (eventSource == buttonCompareVersion) {
            if (comboCompareVersion.isEnabled()) {
                comboCompareVersion.setEnabled(false);
            } else {
                comboCompareVersion.setEnabled(true);
            }
        } else if (eventSource == configurationLink) {
            IWorkbenchPreferenceContainer container= (IWorkbenchPreferenceContainer)getContainer();
            container.openPage(Constants.CONFIGURATION_PROPERTY_PAGE_ID, null);
        }
    }
    
    /**
     * @see eu.sqooss.plugin.properties.EnabledState#setEnabled(boolean)
     */
    public void setEnabled(boolean isEnabled) {
        if (isEnabled) {
            if (controlEnableState != null) {
                //remove the configuration link
                configurationLink.dispose();
                configurationLink = null;
                parent.layout();
            }
        }else {
            if (controlEnableState == null) {
                //add configuration link
                configurationLink = new Link(parent, SWT.NONE);
                configurationLink.setText(PropertyPagesMessages.ProjectPropertyPage_Link_Configuration);
                configurationLink.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
                configurationLink.addSelectionListener(this);
                parent.layout();
            }
        }
        super.setEnabled(isEnabled);
    }
    
    
    private String getEntityPath() {
        IResource resource = (IResource) (getElement().getAdapter(IResource.class));
        return ProjectConverterUtility.getEntityPath(resource);
    }

}

//vi: ai nosi sw=4 ts=4 expandtab

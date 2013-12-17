package org.jenkinsci.plugins.mavenversioncolumn;

import hudson.Extension;
import hudson.maven.MavenModuleSet;
import hudson.model.Descriptor;
import hudson.model.Descriptor.FormException;
import hudson.model.Job;
import hudson.views.ListViewColumn;
import java.util.ResourceBundle;
import net.sf.json.JSONObject;
import org.kohsuke.stapler.StaplerRequest;

/**
 *
 * @author christer.a.moren@gmail.com
 */
public class MavenVersionColumn extends ListViewColumn {
    @Extension
    public static final Descriptor<ListViewColumn> DESCRIPTOR = new DescriptorImpl();

    @Override
    public Descriptor<ListViewColumn> getDescriptor() {
        return DESCRIPTOR;
    }

    public String getVersion(Job job) {
        String result = "";
        if (job instanceof MavenModuleSet) {
            MavenModuleSet ms = (MavenModuleSet) job;
            try {
                result = ms.getLastBuild().getProject().getRootModule().getVersion();
                /*
                 for (MavenModule mm : ms.getModules()) {

                 }
                 */
            } catch (Throwable t) {
                result = "N/A";
            }
        }
        return result;
    }

    private static class DescriptorImpl extends Descriptor<ListViewColumn> {

        @Override
        public ListViewColumn newInstance(StaplerRequest req, JSONObject formData) throws FormException {
            return new MavenVersionColumn();
        }

        @Override
        public String getDisplayName() {
            return "Maven Version";
        }
    }
}

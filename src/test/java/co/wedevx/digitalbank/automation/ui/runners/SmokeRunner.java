package co.wedevx.digitalbank.automation.ui.runners;

import io.cucumber.core.backend.Glue;
import org.junit.platform.suite.api.*;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "steps")
@IncludeTags("dry")

public class SmokeRunner {
}

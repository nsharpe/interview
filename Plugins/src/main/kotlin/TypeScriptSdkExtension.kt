import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property

interface TypeScriptSdkExtension {
    val specFile: RegularFileProperty
    val npmName: Property<String>
}
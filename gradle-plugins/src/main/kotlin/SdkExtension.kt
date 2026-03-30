import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property

interface SdkExtension {
    val basePackage: Property<String>
    val specFile: RegularFileProperty
}
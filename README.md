# Kotlin + Gradle + Spring Webflux + Angular
![template-logos.png](docs%2Ftemplate-logos.png)
<div style="border: 1px solid #0f0f46; border-radius: 4px; padding: 12px; display: flex; background: #4ba1ef; color: #0f0f46;">
    <div style="padding-right: 8px">ℹ️</div><div>In IntelliJ and WebStorm, you can use the common objects
    within a template, but they will not be recognized by the IDE. 
    It will still work, but it will be errored out on the IDE. I have
    not tried this in VSCode.</div>
</div>


## Customize

There are many places that you must change to make it work
as your own project name.

1. Top level project name
2. `src/commonMain` -> `io/violabs/application`
3. `src/jsMain` -> `io/violabs/application`
4. `src/jvmMain` -> `io/violabs/application`
5. `src/jvmMain/group/com/artifact` -> `KotlinSpringAngularApplication.kt`
   * Don't forget to rename the contents
6. `angular.json` - replace `test-bun` with name of frontend
7. `build.gradle.kts`
   * `group`
   * line 101 - reference to application
8. `settings.gradle.kts`
   * `rootProject.name`
9. `tsconfig.json`
   * `"common": [ "./build/js/packages/kotlin-spring-angular/kotlin/kotlin-spring-angular" ]`

### Changes from standard Angular

#### Typescript Location

All the angular `Typescript` resides in the `src/jsMain/typescript` directory with the same structure as
a spring application (`group/company/project-name`)

### Adapted from the following

https://stackoverflow.com/questions/75377055/how-to-add-angular-to-kotlin-multiplatform-full-stack-application
package com.taobao.android.builder.tasks.transform;

import com.android.annotations.NonNull;
import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent.ContentType;
import com.android.build.api.transform.QualifiedContent.Scope;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.gradle.internal.pipeline.TransformManager;
import com.android.utils.FileUtils;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;
import com.taobao.android.builder.tools.zip.ZipUtils;

import org.apache.commons.io.FilenameUtils;
import org.gradle.api.Project;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

/**
 * Created by chenhjohn on 2017/4/22.
 */

public class ApTransform extends Transform {

    private Project project;

    private File incrementalApkDir;

    private File baseApk;

    public ApTransform(Project project, File baseApk) {
        this.baseApk = baseApk;
        this.incrementalApkDir = new File(FilenameUtils.removeExtension(baseApk.getAbsolutePath()) + "_");
        this.project = project;
    }

    public File getIncrementalApkDir() {
        return incrementalApkDir;
    }

    @NonNull
    @Override
    public String getName() {
        return "aPJar";
    }

    @NonNull
    @Override
    public Set<ContentType> getInputTypes() {
        return TransformManager.CONTENT_DEX;
    }

    @NonNull
    @Override
    public Set<Scope> getScopes() {
        return TransformManager.EMPTY_SCOPES;
    }

    @NonNull
    @Override
    public Set<Scope> getReferencedScopes() {
        return Sets.immutableEnumSet(Scope.PROJECT, new Scope[0]);
    }

    @Override
    public Set<ContentType> getOutputTypes() {
        return TransformManager.CONTENT_RESOURCES;
    }

    @Override
    public boolean isIncremental() {
        // TODO make incremental
        return false;
    }

    @NonNull
    @Override
    public Collection<File> getSecondaryDirectoryOutputs() {
        return ImmutableList.of(incrementalApkDir);
    }

    @Override
    public void transform(@NonNull TransformInvocation invocation) throws IOException, TransformException, InterruptedException {
        // FileUtils.copyFile(baseApk, incrementalApkDir);
        ZipUtils.unzip(baseApk, incrementalApkDir.getAbsolutePath());
        Set<File> incrementalDexes = project.fileTree(ImmutableMap.of("dir",
                                                                      incrementalApkDir,
                                                                      "includes",
                                                                      ImmutableList.of(
                                                                              "classes*.dex")))
                .getFiles();
        for (TransformInput input : invocation.getReferencedInputs()) {
            for (JarInput jarInput : input.getJarInputs()) {
            }
            for (DirectoryInput directoryInput : input.getDirectoryInputs()) {
                Set<File> dexes = project.fileTree(ImmutableMap.of("dir",
                                                                   directoryInput.getFile(),
                                                                   "includes",
                                                                   ImmutableList.of("classes*.dex")))
                        .getFiles();
                int size = dexes.size() + incrementalDexes.size();
                for (File file : incrementalDexes) {
                    FileUtils.renameTo(file,
                                       new File(incrementalApkDir, "classes" + size + ".dex"));
                    size--;
                }
            }
        }
    }

}

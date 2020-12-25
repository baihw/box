/*
 * Copyright (c) 2019-present, wee0.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wee0.box.plugin.maven;

import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.collection.CollectRequest;
import org.eclipse.aether.collection.DependencyCollectionException;
import org.eclipse.aether.connector.basic.BasicRepositoryConnectorFactory;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.aether.impl.DefaultServiceLocator;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;
import org.eclipse.aether.resolution.DependencyRequest;
import org.eclipse.aether.resolution.DependencyResolutionException;
import org.eclipse.aether.spi.connector.RepositoryConnectorFactory;
import org.eclipse.aether.spi.connector.transport.TransporterFactory;
import org.eclipse.aether.transport.file.FileTransporterFactory;
import org.eclipse.aether.transport.http.HttpTransporterFactory;
import org.eclipse.aether.util.graph.visitor.PreorderNodeListGenerator;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/5/17 22:22
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class MavenPluginManagerTest {

    public static void main(String[] args) throws DependencyCollectionException, DependencyResolutionException {
        DefaultServiceLocator _locator = MavenRepositorySystemUtils.newServiceLocator();
        _locator.addService(RepositoryConnectorFactory.class, BasicRepositoryConnectorFactory.class);
        _locator.addService(TransporterFactory.class, FileTransporterFactory.class);
        _locator.addService(TransporterFactory.class, HttpTransporterFactory.class);
        RepositorySystem _system = _locator.getService(RepositorySystem.class);

        LocalRepository _local = new LocalRepository("D:\\repo\\m2");
        DefaultRepositorySystemSession _session = MavenRepositorySystemUtils.newSession();
        _session.setLocalRepositoryManager(_system.newLocalRepositoryManager(_session, _local));

        String _centralUrl = "https://repo1.maven.org/maven2/";
        RemoteRepository _central = new RemoteRepository.Builder("central", "default", _centralUrl).build();

//        Artifact _artifact = new DefaultArtifact("com.wee0.box:box-api:[0,)");
        Artifact _artifact = new DefaultArtifact("com.wee0.box:box-impl:0.2.7");
        Dependency _dependency = new Dependency(_artifact, "compile");

        CollectRequest _collectRequest = new CollectRequest();
        _collectRequest.setRoot(_dependency);
        _collectRequest.addRepository(_central);

        DependencyNode _dependencyNode = _system.collectDependencies(_session, _collectRequest).getRoot();
        DependencyRequest _dependencyRequest = new DependencyRequest();
        _dependencyRequest.setRoot(_dependencyNode);

        _system.resolveDependencies(_session, _dependencyRequest);
        PreorderNodeListGenerator _nodeListGenerator = new PreorderNodeListGenerator();
        _dependencyNode.accept(_nodeListGenerator);
        System.out.println(_nodeListGenerator.getClassPath());
        System.out.println("------------------");
        System.out.println(_nodeListGenerator.getFiles());
    }
}

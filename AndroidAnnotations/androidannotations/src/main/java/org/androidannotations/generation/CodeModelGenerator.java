/**
 * Copyright (C) 2010-2013 eBusiness Information, Excilys Group
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed To in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.androidannotations.generation;

import java.io.IOException;

import javax.annotation.processing.Filer;

import org.androidannotations.processing.ModelProcessor.ProcessResult;

import com.sun.codemodel.writer.PrologCodeWriter;

public class CodeModelGenerator {

	private final Filer filer;
	private final String aaVersion;

	public CodeModelGenerator(Filer filer, String aaVersion) {
		this.filer = filer;
		this.aaVersion = aaVersion;
	}

	public void generate(ProcessResult processResult) throws IOException {

		ApiCodeGenerator apiCodeGenerator = new ApiCodeGenerator(filer);
		apiCodeGenerator.writeApiClasses(processResult.apiClassesToGenerate, processResult.originatingElements);

		SourceCodewriter sourceCodeWriter = new SourceCodewriter(filer, processResult.originatingElements);

		PrologCodeWriter prologCodeWriter = new PrologCodeWriter(sourceCodeWriter, "DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations " + aaVersion + ".\n");

		processResult.codeModel.build(prologCodeWriter, new ResourceCodeWriter(filer));
	}
}

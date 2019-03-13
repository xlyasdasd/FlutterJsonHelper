package com.lyle.plugin.flutter.json;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiUtilBase;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.JFrame;

public class FlutterToJsonAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        if (anActionEvent == null) {
            return;
        }
        Editor editor = anActionEvent.getData(PlatformDataKeys.EDITOR);
        Project project = anActionEvent.getData(PlatformDataKeys.PROJECT);
        if (editor == null || project == null) {
            return;
        }
        PsiFile psiFile = PsiUtilBase.getPsiFileInEditor(editor, project);
        if (psiFile == null) {
            return;
        }
        VirtualFile virtualFile = psiFile.getVirtualFile();
        FrameView frameView = new FrameView(virtualFile);
        JFrame jFrame = new JFrame("format json to dart model");
        frameView.setFrame(jFrame);
        jFrame.setSize(700, 470);
        jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jFrame.setVisible(true);
        jFrame.add(frameView.build());
    }
    @Override
    public void update(@NotNull AnActionEvent e) {
        super.update(e);
        e.getPresentation().setEnabled(false);
        Project project = e.getProject();
        if (project != null) {
            Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
            PsiFile file = PsiUtilBase.getPsiFileInEditor(editor, project);
            if (file != null) {
                String fileName = file.getName();
                if (fileName.contains(".dart")) {
                    e.getPresentation().setEnabled(true);
                }
            }
        }
    }
}


package haj.com.service;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import haj.com.dao.TasksDAO;
import haj.com.entity.Tasks;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class TaskTreeService.
 */
public class TaskTreeService extends AbstractService {

	private TasksDAO dao = new TasksDAO();

	public TreeNode buildTree(Tasks task) throws Exception {
		Tasks t = new Tasks();
		t.setDescription("ROOT");
		TreeNode root = new DefaultTreeNode(t, null);
		root.setExpanded(true);
		startTreeBuild(root,task);
		return root;
	}

	private void startTreeBuild(TreeNode root, Tasks task) throws Exception {
		if (task.getPreviousTask()!=null) {
			Tasks rootTask = findRoot(task);
			buildTree(root,rootTask,task);
		}
		else {
			buildTree(root,task,task);
		}




	}

	private void buildTree(TreeNode root, Tasks rootTask, Tasks task) throws Exception {
		DefaultTreeNode node ;
		if (rootTask.equals(task)) {
			 node = new DefaultTreeNode("selectedTask",rootTask, root);
		}
		else {
			 node = new DefaultTreeNode(rootTask, root);
		}
		node.setExpanded(true);
		List<Tasks> children = dao.findByParentTask(rootTask.getId());
		for (Tasks child : children) {
			buildTree(node,child,task);
		}
	}

	public List<Tasks> buildRelatedTasks(Tasks task) throws Exception{
		return buildRelatedTasks(null,findRoot(task), task);
	}

	private List<Tasks> buildRelatedTasks(List<Tasks> taskList,Tasks task,Tasks initialTask) throws Exception{
		if (taskList==null) taskList = new ArrayList<Tasks>();
		if (task.equals(initialTask)) task.setCss("fa-2x red");
		taskList.add(task);
		List<Tasks> children = dao.findByParentTask(task.getId());
		for (Tasks child : children) {
			buildRelatedTasks(taskList,child,initialTask);
		}
		return taskList ;
	}

	public Tasks findRoot(Tasks task) throws Exception {
		if (task.getPreviousTask()!=null) {
		  task = dao.findByKey(task.getPreviousTask().getId());
		  return findRoot(task);
		}
		return task;
	}

}

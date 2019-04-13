package Swing;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.undo.UndoManager;

public class TextArearMenu extends JTextArea implements MouseListener {
	private static final long	serialVersionUID	= 1L;
	private JPopupMenu		pm			= null;
	private JMenuItem		copy			= null;
	private JMenuItem		delete			= null;
	private JMenuItem		cut			= null;
	private JMenuItem		undo			= null;
	private JMenuItem		paste			= null;
	private JMenuItem		redo			= null;
	private JMenuItem		selectAll		= null;
	/* 添加撤消管理器 */
	private UndoManager um = new UndoManager();
	public TextArearMenu()
		{
		super();
		init();
	}
	public TextArearMenu( String str )
		{
		super(str);
		init();
	}
	private void init()
		{
		this.addMouseListener( this );
		this.getDocument().addUndoableEditListener( um );
		pm = new JPopupMenu();
		/* 全选 */
		selectAll = new JMenuItem( "全选" );
		selectAll.addActionListener( new ActionListener()
							     {
			public void actionPerformed( ActionEvent e )
									     {
				action( e );
			}
		}
		);
		/* 粘贴 */
		paste = new JMenuItem( "粘贴" );
		paste.addActionListener( new ActionListener()
							 {
			public void actionPerformed( ActionEvent e )
									 {
				action( e );
			}
		}
		);
		/*复制 */
		copy = new JMenuItem( "复制" );
		copy.addActionListener( new ActionListener()
							{
			public void actionPerformed( ActionEvent e )
									{
				action( e );
			}
		}
		);
		/*删除 */
		delete = new JMenuItem( "删除" );
		delete.addActionListener( new ActionListener()
							  {
			public void actionPerformed( ActionEvent e )
									  {
				action( e );
			}
		}
		);
		/* 剪切 */
		cut = new JMenuItem( "剪切" );
		cut.addActionListener( new ActionListener()
						       {
			public void actionPerformed( ActionEvent e )
								       {
				action( e );
			}
		}
		);
		/* 撤消 */
		undo = new JMenuItem( "撤消" );
		undo.addActionListener( new ActionListener()
							{
			public void actionPerformed( ActionEvent e )
									{
				action( e );
			}
		}
		);
		/* 返回 */
		redo = new JMenuItem( "返回" );
		redo.addActionListener( new ActionListener()
							{
			public void actionPerformed( ActionEvent e )
									{
				action( e );
			}
		}
		);
		pm.add( selectAll );
		pm.add( delete );
		pm.add( new JSeparator() );
		pm.add( copy );
		pm.add( cut );
		pm.add( paste );
		pm.add( new JSeparator() );
		pm.add( undo );
		pm.add( redo );
		this.add( pm );
	}
	/*  */
	public Boolean isClipboardString()
		{
		Boolean		b		= false;
		Clipboard	clipboard	= this.getToolkit().getSystemClipboard();
		Transferable	content		= clipboard.getContents( this );
		try {
			if ( content.getTransferData( DataFlavor.stringFlavor ) instanceof String )
							b = true;
		}
		catch ( Exception e )
				{
		}
		return(b);
	}
	/*  */
	public Boolean isCanCopy()
		{
		Boolean b	= false;
		int	start	= this.getSelectionStart();
		int	end	= this.getSelectionEnd();
		if ( start != end )
				{
			b = true;
		}
		return(b);
	}
	@Override
		public void mouseClicked( MouseEvent arg0 )
		{
	}
	@Override
		public void mouseEntered( MouseEvent arg0 )
		{
	}
	@Override
		public void mouseExited( MouseEvent arg0 )
		{
	}
	@Override
		public void mousePressed( MouseEvent e )
		{
		if ( e.getButton() == MouseEvent.BUTTON3 )
				{
			delete.setEnabled( isCanCopy() );
			copy.setEnabled( isCanCopy() );
			paste.setEnabled( isClipboardString() );
			cut.setEnabled( isCanCopy() );
			undo.setEnabled( um.canUndo() );
			redo.setEnabled( um.canRedo() );
			pm.show( this, e.getX(), e.getY() );
		}
	}
	@Override
		public void mouseReleased( MouseEvent arg0 )
		{
	}
	public void action( ActionEvent e )
		{
		String str = e.getActionCommand();
		if ( str.equals( copy.getText() ) )
				{
			/* 复制 */
			this.copy();
		} else if ( str.equals( paste.getText() ) )
				{
			/* 粘贴 */
			this.paste();
		} else if ( str.equals( cut.getText() ) )
				{
			/* 剪切 */
			this.cut();
		} else if ( str.equals( undo.getText() ) )
				{
			um.undo();
		} else if ( str.equals( redo.getText() ) )
				{
			um.redo();
		} else if ( str.equals( delete.getText() ) )
				{
			this.replaceSelection( "" );
		} else if ( str.equals( selectAll.getText() ) )
				{
			this.selectAll();
		}
	}
}